package dao.models

import java.sql.ResultSet

class ClassScheduleDaoModel(resultSet: ResultSet) {
    val day: Int = resultSet.getInt("day_of_week")
    val startTime: String = resultSet.getString("start_time")
    val endTime: String = resultSet.getString("end_time")
    val subject: String = resultSet.getString("name")
    val room: Int = resultSet.getInt("room")
    val teacherSurname: String = resultSet.getString("last_name")
    val teacherName: String = resultSet.getString("first_name")
    val middleName: String = resultSet.getString("middle_name")

    companion object {
        val classSchedulesFromResultSet: (ResultSet) -> List<ClassScheduleDaoModel> = {
            val list = mutableListOf<ClassScheduleDaoModel>()
            while (it.next()) {
                list.add(ClassScheduleDaoModel(it))
            }
            list
        }
    }
}