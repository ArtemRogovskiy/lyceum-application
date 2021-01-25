package dao.models

import java.sql.ResultSet

class UserRoleDaoModel(resultSet: ResultSet) {
    val user_id: String = resultSet.getString("user_id")
    val role_id: Int = resultSet.getInt("role_id")

    companion object {
        val userRoleFromResultSet: (ResultSet) -> List<UserRoleDaoModel> = {
            val list = mutableListOf<UserRoleDaoModel>()
            while (it.next()) {
                list.add(UserRoleDaoModel(it))
            }
            list
        }
    }
}