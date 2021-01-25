package dao.models

import java.sql.ResultSet

class UserDaoModel(
    val id: String,
    val userName: String,
    val email: String,
    val password: String,
    val lastName: String,
    val firstName: String,
    val middleName: String,
    val createTime: String,
    val classId: String?,
    val statusId: Int
) {
    companion object {
        val userFromResultSet: (ResultSet) -> List<UserDaoModel> = {
            val list = mutableListOf<UserDaoModel>()
            while (it.next()) {
                list.add(
                    UserDaoModel(
                        it.getString("id"),
                        it.getString("username"),
                        it.getString("email"),
                        it.getString("password"),
                        it.getString("last_name"),
                        it.getString("first_name"),
                        it.getString("middle_name"),
                        it.getString("create_time"),
                        it.getString("class_id"),
                        it.getInt("user_status_id")
                    )
                )
            }
            list
        }
    }
}