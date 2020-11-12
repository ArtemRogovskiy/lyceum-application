package dao.models

import java.sql.ResultSet

class ClassSchedule(resultSet: ResultSet) {
    val day: Int = resultSet.getInt("day_of_week")
    val startTime: String = resultSet.getString("start_time")
    val endTime: String = resultSet.getString("end_time")
    val subject: String = resultSet.getString("name")
    val room: Int = resultSet.getInt("room")
    val teacherName: String = resultSet.getString("first_name")
    val teacherSurname: String = resultSet.getString("last_name")

    companion object {
        val classSchedulesFromResultSet: (ResultSet) -> List<ClassSchedule> = {
            val list = mutableListOf<ClassSchedule>()
            while (it.next()) {
                list.add(ClassSchedule(it))
            }
            list
        }
    }
}