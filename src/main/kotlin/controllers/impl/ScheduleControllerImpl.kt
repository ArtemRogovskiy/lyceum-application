package controllers.impl

import controllers.ScheduleController
import dao.models.ClassSchedule
import dao.models.TeacherSchedule
import getLogger
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import services.ScheduleService

class ScheduleControllerImpl(
    private val routing: Routing,
    private val scheduleService: ScheduleService
) : ScheduleController {

    private fun Routing.schedule() {
        // localhost:8080/schedule/class?classNumber=10&classLetter=а
        get("/schedule/class") {
            val queryParameters = call.request.queryParameters
            val classNumberParam = queryParameters["classNumber"]?.toInt()
            val classLetterParam = queryParameters["classLetter"]?.toUpperCase()
            if (classNumberParam == null || classLetterParam == null) {
                getLogger().warn("Wrong parameters. Expected 'classNumber' type of Int and 'classLetter' type of String")
                call.respond(HttpStatusCode.NotFound)
            } else {
                call.respond(getClassSchedule(classNumberParam, classLetterParam))
            }
        }

        // localhost:8080/schedule/teacher?teacherId=6e5cd906-27e8-11eb-aa2f-0242ac140002
        get("/schedule/teacher") {
            val queryParameters = call.request.queryParameters
            val teacherIdParam = queryParameters["teacherId"]
            if (teacherIdParam == null) {
                getLogger().warn("Wrong parameter. Expected 'teacherId' type of UUID")
                call.respond(HttpStatusCode.NotFound)
            } else {
                call.respond(getTeacherSchedule(teacherIdParam))
            }
        }
    }

    fun addRouts() {
        routing.schedule()
    }

    override fun getClassSchedule(classNumber: Int, classLetter: String): List<ClassSchedule> {
        return scheduleService.getClassSchedule(classNumber, classLetter)
    }

    override fun getTeacherSchedule(teacherId: String): List<TeacherSchedule> {
        return scheduleService.getTeacherSchedule(teacherId)
    }
}