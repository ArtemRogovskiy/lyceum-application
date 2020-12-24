package dao.models

import java.sql.ResultSet

class UserStatusDaoModel(resultSet: ResultSet) {
    val id: Int = resultSet.getInt("id")
    val name: String = resultSet.getString("name")

    companion object {
        val userStatusFromResultSet: (ResultSet) -> List<UserStatusDaoModel> = {
            val list = mutableListOf<UserStatusDaoModel>()
            while (it.next()) {
                list.add(UserStatusDaoModel(it))
            }
            list
        }
    }
}