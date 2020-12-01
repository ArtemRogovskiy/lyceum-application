package controllers.impl

import controllers.ScheduleController
import controllers.models.ClassSchedule
import dao.models.ClassScheduleDaoModel
import dao.models.ScheduleDaoModel
import dao.models.TeacherScheduleDaoModel
import getLogger
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import services.ScheduleService
import java.util.*

class ScheduleControllerImpl(
    private val routing: Routing,
    private val scheduleService: ScheduleService
) : ScheduleController {

    private fun Routing.schedule() {
        route("/schedules") {
            // http://localhost:8080/schedules/7b89ea87-27e8-11eb-aa2f-0242ac140002
            get("/{id}") {
                val scheduleId = call.parameters["id"]
                scheduleId ?: getLogger().warn("Empty path parameter.")
                call.respond(getSchedule(UUID.fromString(scheduleId)))
            }

            // http://localhost:8080/schedules/class?classNumber=10&classLetter=а
            get("/class") {
                val queryParameters = call.request.queryParameters
                val classNumberParam = queryParameters["classNumber"]?.toInt()
                val classLetterParam = queryParameters["classLetter"]?.toUpperCase()
                if (classNumberParam == null || classLetterParam == null) {
                    getLogger().warn("Wrong parameters. Expected 'classNumber' type of Int and 'classLetter' type of String")
                    call.respond(HttpStatusCode.NotFound)
                } else {
                    val response = getClassSchedule(classNumberParam, classLetterParam)
                    call.respond(response)
                }
            }

            // http://localhost:8080/schedules/teacher?teacherId=6e5cd906-27e8-11eb-aa2f-0242ac140002
            get("/teacher") {
                val queryParameters = call.request.queryParameters
                val teacherIdParam = queryParameters["teacherId"]
                if (teacherIdParam == null) {
                    getLogger().warn("Wrong parameter. Expected 'teacherId' type of UUID")
                    call.respond(HttpStatusCode.NotFound)
                } else {
                    val response = getTeacherSchedule(UUID.fromString(teacherIdParam))
                    call.respond(response)
                }
            }

            post {
                val schedule = call.receive<ClassSchedule>()
                val id = addSchedule(schedule)
                call.respond(HttpStatusCode.Created, id)
            }

            put("/{id}") {
                val scheduleId = call.parameters["id"]
                val updatedSchedule = call.receive<ClassSchedule>()
                scheduleId ?: getLogger().warn("Empty path parameter.")
                updateSchedule(UUID.fromString(scheduleId), updatedSchedule)
                call.respond(HttpStatusCode.OK)
            }

            delete("/{id}") {
                val scheduleId = call.parameters["id"]
                scheduleId ?: getLogger().warn("Empty path parameter.")
                deleteSchedule(UUID.fromString(scheduleId))
                call.respond(HttpStatusCode.Accepted)
            }
        }
    }

    fun addRouts() {
        routing.schedule()
    }

    override fun getSchedule(scheduleId: UUID): ScheduleDaoModel {
        return scheduleService.getSchedule(scheduleId)
    }

    override fun getClassSchedule(classNumber: Int, classLetter: String): List<ClassScheduleDaoModel> {
        return scheduleService.getClassSchedule(classNumber, classLetter)
    }

    override fun getTeacherSchedule(teacherId: UUID): List<TeacherScheduleDaoModel> {
        return scheduleService.getTeacherSchedule(teacherId)
    }

    override fun addSchedule(classSchedule: ClassSchedule): UUID {
        return scheduleService.addSchedule(classSchedule)
    }

    override fun updateSchedule(scheduleId: UUID, classSchedule: ClassSchedule) {
        scheduleService.updateSchedule(scheduleId, classSchedule)
    }

    override fun deleteSchedule(scheduleId: UUID) {
        scheduleService.deleteSchedule(scheduleId)
    }
}