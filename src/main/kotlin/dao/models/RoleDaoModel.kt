package dao.models

import java.sql.ResultSet

class RoleDaoModel(
    val role: String,
    val userId: String
) {
    companion object {
        val rolesFromResultSet: (ResultSet) -> List<RoleDaoModel> = {
            val list = mutableListOf<RoleDaoModel>()
            while (it.next()) {
                list.add(
                    RoleDaoModel(
                        it.getString("role"),
                        it.getString("user_id")
                    )
                )
            }
            list
        }
    }
}