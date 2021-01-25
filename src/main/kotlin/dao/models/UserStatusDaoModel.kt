package dao.models

import java.sql.ResultSet

class UserStatusDaoModel(
    val userId: String,
    val status: String
) {
    companion object {
        val userStatusFromResultSet: (ResultSet) -> List<UserStatusDaoModel> = {
            val list = mutableListOf<UserStatusDaoModel>()
            while (it.next()) {
                list.add(
                    UserStatusDaoModel(
                        it.getString("user_id"),
                        it.getString("status")
                    )
                )
            }
            list
        }
    }
}