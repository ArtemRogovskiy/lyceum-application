package dao

import controllers.models.ClassSchedule
import dao.models.ClassScheduleDaoModel
import dao.models.ScheduleDaoModel
import dao.models.TeacherScheduleDaoModel
import java.util.*

interface ScheduleDao {

    suspend fun getSchedule(scheduleId: UUID): ScheduleDaoModel

    suspend fun getClassSchedule(classNumber: Int, classLetter: String): List<ClassScheduleDaoModel>

    suspend fun getTeacherSchedule(teacherId: UUID): List<TeacherScheduleDaoModel>

    suspend fun addSchedule(classSchedule: ClassSchedule): UUID

    suspend fun updateSchedule(scheduleId: UUID, classSchedule: ClassSchedule)

    suspend fun deleteSchedule(scheduleId: UUID)
}