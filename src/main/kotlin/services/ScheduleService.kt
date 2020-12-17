package services

import controllers.models.ClassSchedule
import dao.models.ClassScheduleDaoModel
import dao.models.ScheduleDaoModel
import dao.models.TeacherScheduleDaoModel
import java.util.*

interface ScheduleService {
    fun getSchedule(scheduleId: UUID): ScheduleDaoModel

    fun getClassSchedule(classNumber: Int, classLetter: String): List<ClassScheduleDaoModel>

    fun getTeacherSchedule(teacherId: UUID): List<TeacherScheduleDaoModel>

    fun addSchedule(classSchedule: ClassSchedule): UUID

    fun updateSchedule(scheduleId: UUID, classSchedule: ClassSchedule)

    fun deleteSchedule(scheduleId: UUID)
}