package services.impl

import controllers.models.SchoolClassModel
import dao.SchoolClassDao
import dao.models.SchoolClassDaoModel
import services.SchoolClassService
import java.util.*

class SchoolClassServiceImpl(private val schoolClassDao: SchoolClassDao) : SchoolClassService {
    override suspend fun getAllSchoolClasses(): List<SchoolClassDaoModel> {
        return schoolClassDao.getAllSchoolClasses()
    }

    override suspend fun getSchoolClass(schoolClassId: UUID): SchoolClassDaoModel {
        return schoolClassDao.getSchoolClass(schoolClassId)
    }

    override suspend fun addSchoolClass(schoolClass: SchoolClassModel): UUID {
        return schoolClassDao.addSchoolClass(schoolClass)
    }

    override suspend fun updateSchoolClass(schoolClassId: UUID, updatedSchoolClass: SchoolClassModel) {
        schoolClassDao.updateSchoolClass(schoolClassId, updatedSchoolClass)
    }

    override suspend fun deleteSchedule(schoolClassId: UUID) {
        schoolClassDao.deleteSchedule(schoolClassId)
    }
}