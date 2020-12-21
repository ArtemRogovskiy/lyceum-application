package controllers.models

import java.util.*

data class SchoolClassModel(
    val classId: UUID, val number: Int, val letter: String, val formMaster: UUID?, val classMonitor: UUID?
)
