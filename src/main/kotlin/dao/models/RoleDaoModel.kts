package dao.models

import java.sql.ResultSet

class RoleDaoModel(resultSet: ResultSet) {
    val id: Int = resultSet.getInt("id")
    val name: String = resultSet.getString("name")

    companion object {
        val rolesFromResultSet: (ResultSet) -> List<RoleDaoModel> = {
            val list = mutableListOf<RoleDaoModel>()
            while (it.next()) {
                list.add(RoleDaoModel(it))
            }
            list
        }
    }
}