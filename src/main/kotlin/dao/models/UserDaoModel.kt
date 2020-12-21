package dao.models

import java.sql.ResultSet

class UserDaoModel(resultSet: ResultSet) {
    val id: String = resultSet.getString("id")
    val userName: String = resultSet.getString("username")
    val email: String = resultSet.getString("email")
    val password: String = resultSet.getString("password")
    val firstName: String = resultSet.getString("last_name")
    val middleName: String = resultSet.getString("middle_name")
    val lastName: String = resultSet.getString("last_name")
    val classId: String = resultSet.getString("class_id")
    val statusId: Int = resultSet.getInt("user_status_id")

    companion object {
        val usersFromResultSet: (ResultSet) -> List<UserDaoModel> = {
            val list = mutableListOf<UserDaoModel>()
            while (it.next()) {
                list.add(UserDaoModel(it))
            }
            list
        }
    }
}