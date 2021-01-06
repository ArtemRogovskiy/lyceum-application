package dao.models

import java.sql.ResultSet

data class ClassScheduleDaoModel(
    val day: Int,
    val startTime: String,
    val endTime: String,
    val subject: String,
    val room: Int,
    val teacherSurname: String,
    val teacherName: String,
    val middleName: String
) {
    companion object {
        val classSchedulesFromResultSet: (ResultSet) -> List<ClassScheduleDaoModel> = {
            val list = mutableListOf<ClassScheduleDaoModel>()
            while (it.next()) {
                list.add(
                    ClassScheduleDaoModel(
                        it.getInt("day_of_week"),
                        it.getString("start_time"),
                        it.getString("end_time"),
                        it.getString("name"),
                        it.getInt("room"),
                        it.getString("last_name"),
                        it.getString("first_name"),
                        it.getString("middle_name")
                    )
                )
            }
            list
        }
    }
}