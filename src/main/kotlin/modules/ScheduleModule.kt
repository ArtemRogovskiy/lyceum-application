package modules

import controllers.models.ClassSchedule
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject
import services.ScheduleService
import services.UserService
import util.Log
import java.text.DateFormat
import java.util.*

@Suppress("unused") // Referenced in application.conf
fun Application.scheduleModule() {
    val userService by inject<UserService>()

    install(CORS) {
        method(HttpMethod.Get)
        method(HttpMethod.Post)
        method(HttpMethod.Put)
        method(HttpMethod.Delete)
        anyHost()
    }
    install(DefaultHeaders)
    install(Compression)
    install(CallLogging)
    install(ContentNegotiation) {
        gson {
            setDateFormat(DateFormat.LONG)
            setPrettyPrinting()
        }
    }
    install(Authentication) {
        basic {
            realm = "lyceum-application"
            validate { credentials ->
                val user = userService.getUserByName(credentials.name)
                if (credentials.password == user.password) {
                    UserIdPrincipal(credentials.name)
                } else null
            }
        }
    }

    val scheduleService by inject<ScheduleService>()

    routing {
        route("/schedules") {
            // http://localhost:8088/schedules/7b89ea87-27e8-11eb-aa2f-0242ac140002
            get("/{scheduleId}") {
                val scheduleId = call.parameters["scheduleId"]
                scheduleId ?: Log.warn("Empty path parameter.")
                call.respond(scheduleService.getSchedule(UUID.fromString(scheduleId)))
            }

            // http://localhost:8088/schedules/class?classNumber=10&classLetter=а
            get("/class") {
                val queryParameters = call.request.queryParameters
                val classNumberParam = queryParameters["classNumber"]?.toInt()
                val classLetterParam = queryParameters["classLetter"]?.toUpperCase()
                if (classNumberParam == null || classLetterParam == null) {
                    Log.warn("Wrong parameters. Expected 'classNumber' type of Int and 'classLetter' type of String")
                    call.respond(HttpStatusCode.NotFound)
                } else {
                    val response = scheduleService.getClassSchedule(classNumberParam, classLetterParam)
                    call.respond(response)
                }
            }

            // http://localhost:8088/schedules/teacher?teacherId=6e5cd906-27e8-11eb-aa2f-0242ac140002
            get("/teacher") {
                val queryParameters = call.request.queryParameters
                val teacherIdParam = queryParameters["teacherId"]
                if (teacherIdParam == null) {
                    Log.warn("Wrong parameter. Expected 'teacherId' type of UUID")
                    call.respond(HttpStatusCode.NotFound)
                } else {
                    val response = scheduleService.getTeacherSchedule(UUID.fromString(teacherIdParam))
                    call.respond(response)
                }
            }

            post {
                val schedule = call.receive<ClassSchedule>()
                val id = scheduleService.addSchedule(schedule)
                call.respond(HttpStatusCode.Created, id)
            }

            put("/{scheduleId}") {
                val scheduleId = call.parameters["scheduleId"]
                val updatedSchedule = call.receive<ClassSchedule>()
                scheduleId ?: Log.warn("Empty path parameter.")
                scheduleService.updateSchedule(UUID.fromString(scheduleId), updatedSchedule)
                call.respond(HttpStatusCode.OK)
            }

            delete("/{scheduleId}") {
                val scheduleId = call.parameters["scheduleId"]
                scheduleId ?: Log.warn("Empty path parameter.")
                scheduleService.deleteSchedule(UUID.fromString(scheduleId))
                call.respond(HttpStatusCode.Accepted)
            }
        }
    }
}