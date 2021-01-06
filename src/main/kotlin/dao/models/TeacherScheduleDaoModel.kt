package dao.models

import java.sql.ResultSet

data class TeacherScheduleDaoModel(
    val day: Int,
    val startTime: String,
    val endTime: String,
    val subject: String,
    val room: Int,
    val classNumber: Int,
    val classLetter: String
) {
    companion object {
        val teacherSchedulesFromResultSet: (ResultSet) -> List<TeacherScheduleDaoModel> = {
            val list = mutableListOf<TeacherScheduleDaoModel>()
            while (it.next()) {
                list.add(
                    TeacherScheduleDaoModel(
                        it.getInt("day_of_week"),
                        it.getString("start_time"),
                        it.getString("end_time"),
                        it.getString("name"),
                        it.getInt("room"),
                        it.getInt("number"),
                        it.getString("letter")
                    )
                )
            }
            list
        }
    }
}