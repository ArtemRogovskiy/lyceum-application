package services

import controllers.models.SchoolClassModel
import dao.models.SchoolClassDaoModel
import java.util.*

interface SchoolClassService {
    suspend fun getAllSchoolClasses(): List<SchoolClassDaoModel>

    suspend fun getSchoolClass(schoolClassId: UUID): SchoolClassDaoModel

    suspend fun addSchoolClass(schoolClass: SchoolClassModel): UUID

    suspend fun updateSchoolClass(schoolClassId: UUID, updatedSchoolClass: SchoolClassModel)

    suspend fun deleteSchedule(schoolClassId: UUID)
}