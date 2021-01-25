package controllers.models

import java.util.*

data class ClassSchedule(
    val teacherId: UUID, val room: Int, val classId: UUID, val subjectId: UUID, val period: Int
)
