package dao.impl

import dao.ScheduleDao
import dao.models.ClassSchedule
import dao.models.TeacherSchedule
import getLogger
import util.executeQuery

class ScheduleDaoImpl : ScheduleDao {

    override fun getClassSchedule(classNumber: Int, classLetter: String): List<ClassSchedule> {
        val query =
            "select p.day_of_week, p.start_time, p.end_time, s.name, cs.room, u.last_name, u.first_name, u.middle_name " +
                    "from mgol.class_schedule cs " +
                    "join mgol.user u " +
                    "on cs.teacher_id = u.id " +
                    "join mgol.subject s " +
                    "on cs.subject_id = s.id " +
                    "join mgol.period p " +
                    "on cs.period_id = p.id " +
                    "join mgol.class c " +
                    "on cs.class_id = c.id " +
                    "where c.letter = '$classLetter' " +
                    "and c.number = $classNumber " +
                    "order by day_of_week, start_time;"
        val schedule =
            executeQuery(query, ClassSchedule.classSchedulesFromResultSet)
        getLogger().info("List of ClassSchedule from db: $schedule")
        return schedule
    }

    override fun getTeacherSchedule(teacherId: String): List<TeacherSchedule> {
        val query = "select p.day_of_week, p.start_time, p.end_time, s.name, cs.room, c.number, c.letter " +
                "from mgol.class_schedule cs " +
                "join mgol.user u " +
                "on cs.teacher_id = u.id " +
                "join mgol.subject s " +
                "on cs.subject_id = s.id " +
                "join mgol.period p " +
                "on cs.period_id = p.id " +
                "join mgol.class c " +
                "on cs.class_id = c.id " +
                "where cs.teacher_id = '$teacherId' " +
                "order by day_of_week, start_time;"
        val schedule =
            executeQuery(query, TeacherSchedule.teacherSchedulesFromResultSet)
        getLogger().info("List of TeacherSchedule from db: $schedule")
        return schedule
    }

}