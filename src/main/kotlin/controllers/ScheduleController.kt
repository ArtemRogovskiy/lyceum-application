package controllers

import dao.models.ClassSchedule
import dao.models.TeacherSchedule

interface ScheduleController {
    fun getClassSchedule(classNumber: Int, classLetter: String): List<ClassSchedule>

    fun getTeacherSchedule(teacherId: Int): List<TeacherSchedule>
}