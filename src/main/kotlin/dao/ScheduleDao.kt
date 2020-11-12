package dao

import dao.models.ClassSchedule
import dao.models.TeacherSchedule

interface ScheduleDao {

    fun getClassSchedule(classNumber: Int, classLetter: String): List<ClassSchedule>

    fun getTeacherSchedule(teacherId: Int): List<TeacherSchedule>
}