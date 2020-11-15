package controllers.impl

import controllers.ScheduleController
import dao.models.ClassSchedule
import dao.models.TeacherSchedule
import getLogger
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.get
import services.ScheduleService

class ScheduleControllerImpl(
    private val routing: Routing,
    private val scheduleService: ScheduleService
) : ScheduleController {

    private fun Routing.schedule() {
        // localhost:8080/schedule/class?classNumber=10&classLetter=a
        get("/schedule/class") {
            val queryParameters = call.request.queryParameters
            val classNumberParam = queryParameters["classNumber"]?.toInt()
            val classLetterParam = queryParameters["classLetter"]?.toUpperCase()
            if (classNumberParam == null || classLetterParam == null) {
                getLogger().warn("Wrong parameters")
                call.respond(HttpStatusCode.NotFound)
            } else {
                call.respond(getClassSchedule(classNumberParam, classLetterParam))
            }
        }

        // localhost:8080/schedule/teacher?teacherId=1
        get("/schedule/teacher") {
            val queryParameters = call.request.queryParameters
            val teacherIdParam = queryParameters["teacherId"]?.toInt()
            if (teacherIdParam == null) {
                getLogger().warn("Wrong parameters")
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

    override fun getTeacherSchedule(teacherId: Int): List<TeacherSchedule> {
        return scheduleService.getTeacherSchedule(teacherId)
    }
}