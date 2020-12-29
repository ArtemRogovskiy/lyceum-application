package dao.models

import java.sql.ResultSet

data class NewsDaoModel(
    val id: String,
    val createdBy: String,
    val title: String,
    val message: String,
    val prover: String,
    val createdTime: String,
    val isApproved: Boolean,
    val notificationTypeId: String
) {
    companion object {
        val newsFromResultSet: (ResultSet) -> List<NewsDaoModel> = {
            val list = mutableListOf<NewsDaoModel>()
            while (it.next()) {
                list.add(
                    NewsDaoModel(
                        it.getString("id"),
                        it.getString("created_by"),
                        it.getString("title"),
                        it.getString("message"),
                        it.getString("approver"),
                        it.getString("create_time"),
                        it.getInt("is_approved") == 1,
                        it.getString("notification_type_id"),
                    )
                )
            }
            list
        }
    }
}
