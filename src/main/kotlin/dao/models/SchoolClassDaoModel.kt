package dao.models

import java.sql.ResultSet

class SchoolClassDaoModel(
    val id: String,
    val number: Int,
    val letter: String,
    val formMasterId: String?,
    val classMonitorId: String?
) {
    companion object {
        val schoolClassFromResultSet: (ResultSet) -> List<SchoolClassDaoModel> = {
            val list = mutableListOf<SchoolClassDaoModel>()
            while (it.next()) {
                list.add(
                    SchoolClassDaoModel(
                        it.getString("id"),
                        it.getInt("number"),
                        it.getString("letter"),
                        it.getString("form_master"),
                        it.getString("class_monitor")
                    )
                )
            }
            list
        }
    }
}