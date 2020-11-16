package dao

import dao.models.ClassSchedule
import dao.models.TeacherSchedule
import java.util.*

interface ScheduleDao {

    fun getClassSchedule(classNumber: Int, classLetter: String): List<ClassSchedule>

    fun getTeacherSchedule(teacherId: String): List<TeacherSchedule>
}