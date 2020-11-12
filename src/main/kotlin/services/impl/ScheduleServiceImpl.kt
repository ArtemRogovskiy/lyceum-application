package services.impl

import dao.ScheduleDao
import dao.models.ClassSchedule
import dao.models.TeacherSchedule
import services.ScheduleService

class ScheduleServiceImpl(private val scheduleDao: ScheduleDao) : ScheduleService {
    override fun getClassSchedule(classNumber: Int, classLetter: String): List<ClassSchedule> {
        return scheduleDao.getClassSchedule(classNumber, classLetter)
    }

    override fun getTeacherSchedule(teacherId: Int): List<TeacherSchedule> {
        return scheduleDao.getTeacherSchedule(teacherId)
    }

}