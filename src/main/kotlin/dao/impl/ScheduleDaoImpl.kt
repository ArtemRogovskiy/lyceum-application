package dao.impl

import controllers.models.ClassSchedule
import dao.ScheduleDao
import dao.models.ClassScheduleDaoModel
import dao.models.ScheduleDaoModel
import dao.models.TeacherScheduleDaoModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import util.Log
import util.executeQuery
import util.executeUpdate
import java.util.*

class ScheduleDaoImpl : ScheduleDao {
    override suspend fun getSchedule(scheduleId: UUID): ScheduleDaoModel {
        val query = """
            select id, teacher_id, room, class_id, subject_id, period_id
                    from mgol.class_schedule
                    where id = '$scheduleId';
        """
        return withContext(Dispatchers.Default) {
            executeQuery(query, ScheduleDaoModel.scheduleFromResultSet)[0]
        }
    }

    override suspend fun getClassSchedule(classNumber: Int, classLetter: String): List<ClassScheduleDaoModel> {
        val query = """
            select p.day_of_week, p.start_time, p.end_time, s.name, cs.room, u.last_name, u.first_name, u.middle_name 
                    from mgol.class_schedule cs
                    join mgol.user u
                    on cs.teacher_id = u.id
                    join mgol.subject s
                    on cs.subject_id = s.id
                    join mgol.period p 
                    on cs.period_id = p.id 
                    join mgol.class c 
                    on cs.class_id = c.id 
                    where c.letter = '$classLetter' 
                    and c.number = $classNumber 
                    order by day_of_week, start_time;
        """
        return withContext(Dispatchers.Default) {
            val schedule = executeQuery(query, ClassScheduleDaoModel.classSchedulesFromResultSet)
            Log.debug("List of ClassSchedule from db: $schedule")
            schedule
        }
    }

    override suspend fun getTeacherSchedule(teacherId: UUID): List<TeacherScheduleDaoModel> {
        val query = """
            select p.day_of_week, p.start_time, p.end_time, s.name, cs.room, c.number, c.letter 
                    from mgol.class_schedule cs 
                    join mgol.user u 
                    on cs.teacher_id = u.id 
                    join mgol.subject s 
                    on cs.subject_id = s.id 
                    join mgol.period p 
                    on cs.period_id = p.id 
                    join mgol.class c 
                    on cs.class_id = c.id 
                    where cs.teacher_id = '$teacherId' 
                    order by day_of_week, start_time;
        """
        return withContext(Dispatchers.Default) {
            val schedule = executeQuery(query, TeacherScheduleDaoModel.teacherSchedulesFromResultSet)
            Log.debug("List of TeacherSchedule from db: $schedule")
            schedule
        }
    }

    override suspend fun addSchedule(classSchedule: ClassSchedule): UUID {
        val id = UUID.randomUUID()
        val query = """
            insert into mgol.class_schedule 
                (id, teacher_id, room, class_id, subject_id, period_id) values('$id', 
                '${classSchedule.teacherId}', ${classSchedule.room}, '${classSchedule.classId}', 
                '${classSchedule.subjectId}', ${classSchedule.period});
        """
        return withContext(Dispatchers.Default) {
            executeUpdate(query)
            id
        }
    }

    override suspend fun updateSchedule(scheduleId: UUID, classSchedule: ClassSchedule) {
        val query = """
            update mgol.class_schedule 
                set teacher_id = '${classSchedule.teacherId}', room = ${classSchedule.room}, 
                class_id = '${classSchedule.classId}', subject_id = '${classSchedule.subjectId}', 
                period_id = ${classSchedule.period} where id = '$scheduleId';
        """
        withContext(Dispatchers.Default) {
            val rowsNum = executeUpdate(query)
            Log.info("$rowsNum rows have been updated")
        }
    }

    override suspend fun deleteSchedule(scheduleId: UUID) {
        val query = """
            delete from mgol.class_schedule where id = '$scheduleId';
        """
        withContext(Dispatchers.Default) {
            val rowsNum = executeUpdate(query)
            Log.info("$rowsNum rows have been deleted")
        }
    }

}