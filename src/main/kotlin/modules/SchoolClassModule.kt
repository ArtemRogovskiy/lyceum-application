package modules

import controllers.models.SchoolClassModel
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject
import services.SchoolClassService
import util.Log
import java.util.*

@Suppress("unused") // Referenced in application.conf
fun Application.schoolClassModule() {
    val schoolClassService by inject<SchoolClassService>()

    routing {
        route("/classes") {
            // http://localhost:8080/classes
            get() {
                val response = schoolClassService.getAllSchoolClasses()
                call.respond(response)
            }

            // http://localhost:8080/classes/660eeba7-27e8-11eb-aa2f-0242ac140002
            get("/{classId}") {
                val classId = call.parameters["classId"]
                classId ?: Log.warn("Empty path parameter: classId")
                call.respond(schoolClassService.getSchoolClass(UUID.fromString(classId)))
            }

            post {
                val schoolClass = call.receive<SchoolClassModel>()
                val id = schoolClassService.addSchoolClass(schoolClass)
                call.respond(HttpStatusCode.Created, id)
            }

            put("/{classId}") {
                val classId = call.parameters["classId"]
                val updatedSchoolClass = call.receive<SchoolClassModel>()
                classId ?: Log.warn("Empty path parameter: classId")
                schoolClassService.updateSchoolClass(UUID.fromString(classId), updatedSchoolClass)
                call.respond(HttpStatusCode.OK)
            }

            delete("/{classId}") {
                val classId = call.parameters["classId"]
                classId ?: Log.warn("Empty path parameter: classId")
                schoolClassService.deleteSchedule(UUID.fromString(classId))
                call.respond(HttpStatusCode.Accepted)
            }
        }
    }
}