package dao.models

import java.sql.ResultSet

class ScheduleDaoModel(resultSet: ResultSet) {
    val id: String = resultSet.getString("id")
    val teacherId: String = resultSet.getString("teacher_id")
    val room: String = resultSet.getString("room")
    val classId: String = resultSet.getString("class_id")
    val subjectId: String = resultSet.getString("subject_id")
    val periodId: Int = resultSet.getInt("period_id")

    companion object {
        val scheduleFromResultSet: (ResultSet) -> List<ScheduleDaoModel> = {
            val list = mutableListOf<ScheduleDaoModel>()
            while (it.next()) {
                list.add(ScheduleDaoModel(it))
            }
            list
        }
    }

}
