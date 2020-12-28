package dao.models

import java.sql.ResultSet

data class ScheduleDaoModel(
    val id: String,
    val teacherId: String,
    val room: String,
    val classId: String,
    val subjectId: String,
    val periodId: Int
) {

    companion object {
        val scheduleFromResultSet: (ResultSet) -> List<ScheduleDaoModel> = {
            val list = mutableListOf<ScheduleDaoModel>()
            while (it.next()) {
                list.add(
                    ScheduleDaoModel(
                        it.getString("id"),
                        it.getString("teacher_id"),
                        it.getString("room"),
                        it.getString("class_id"),
                        it.getString("subject_id"),
                        it.getInt("period_id")
                    )
                )
            }
            list
        }
    }

}
