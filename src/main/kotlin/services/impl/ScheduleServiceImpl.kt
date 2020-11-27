package services.impl

import controllers.models.ClassSchedule
import dao.ScheduleDao
import dao.models.ClassScheduleDaoModel
import dao.models.ScheduleDaoModel
import dao.models.TeacherScheduleDaoModel
import services.ScheduleService
import java.util.*

class ScheduleServiceImpl(private val scheduleDao: ScheduleDao) : ScheduleService {
    override suspend fun getSchedule(scheduleId: UUID): ScheduleDaoModel {
        return scheduleDao.getSchedule(scheduleId)
    }

    override suspend fun getClassSchedule(classNumber: Int, classLetter: String): List<ClassScheduleDaoModel> {
        return scheduleDao.getClassSchedule(classNumber, classLetter)
    }

    override suspend fun getTeacherSchedule(teacherId: UUID): List<TeacherScheduleDaoModel> {
        return scheduleDao.getTeacherSchedule(teacherId)
    }

    override suspend fun addSchedule(classSchedule: ClassSchedule): UUID {
        return scheduleDao.addSchedule(classSchedule)
    }

    override suspend fun updateSchedule(scheduleId: UUID, classSchedule: ClassSchedule) {
        scheduleDao.updateSchedule(scheduleId, classSchedule)
    }

    override suspend fun deleteSchedule(scheduleId: UUID) {
        scheduleDao.deleteSchedule(scheduleId)
    }

}