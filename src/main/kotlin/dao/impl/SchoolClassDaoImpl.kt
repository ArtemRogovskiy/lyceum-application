package dao.impl

import controllers.models.SchoolClassModel
import dao.SchoolClassDao
import dao.models.SchoolClassDaoModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import util.Log
import util.executeQuery
import util.executeUpdate
import java.util.*

class SchoolClassDaoImpl : SchoolClassDao {
    override suspend fun getAllSchoolClasses(): List<SchoolClassDaoModel> {
        val query = """
            select id, number, letter, form_master, class_monitor
                from mgol.class;
        """.trimIndent()
        return withContext(Dispatchers.Default) {
            executeQuery(query, SchoolClassDaoModel.schoolClassFromResultSet)
        }
    }

    override suspend fun getSchoolClass(schoolClassId: UUID): SchoolClassDaoModel {
        val query = """
            select id, number, letter, form_master, class_monitor
                from mgol.class
                where id = "$schoolClassId";
        """.trimIndent()
        return withContext(Dispatchers.Default) {
            executeQuery(query, SchoolClassDaoModel.schoolClassFromResultSet)[0]
        }
    }

    override suspend fun addSchoolClass(schoolClass: SchoolClassModel): UUID {
        val id = UUID.randomUUID()
        val formMaster = convertUUIDToValidString(schoolClass.formMaster)
        val classMonitor = convertUUIDToValidString(schoolClass.classMonitor)
        val query = """
            insert into mgol.class
                (id, number, letter, form_master, class_monitor) 
                values('$id', ${schoolClass.number}, '${schoolClass.letter}', 
                $formMaster, $classMonitor);
        """.trimIndent()
        return withContext(Dispatchers.Default) {
            executeUpdate(query)
            id
        }
    }

    override suspend fun updateSchoolClass(schoolClassId: UUID, updatedSchoolClass: SchoolClassModel) {
        val formMaster = convertUUIDToValidString(updatedSchoolClass.formMaster)
        val classMonitor = convertUUIDToValidString(updatedSchoolClass.classMonitor)
        val query = """
            update mgol.class
                set number = ${updatedSchoolClass.number}, 
                letter = '${updatedSchoolClass.letter}', 
                form_master = $formMaster, 
                class_monitor = $classMonitor 
                where id = '$schoolClassId';
        """.trimIndent()
        withContext(Dispatchers.Default) {
            val rowsNum = executeUpdate(query)
            Log.info("$rowsNum rows have been updated")
        }
    }

    override suspend fun deleteSchedule(schoolClassId: UUID) {
        val query = """
            delete from mgol.class where id = '$schoolClassId';
        """.trimIndent()
        withContext(Dispatchers.Default) {
            val rowsNum = executeUpdate(query)
            Log.info("$rowsNum rows have been deleted from mgol.class")
        }
    }

    private fun convertUUIDToValidString(id: UUID?): String? {
        return if (id != null) {
            "'${id}'"
        } else {
            null
        }
    }
}