package controllers.models

import java.util.*

data class ClassSchedule(
    val teacherId: UUID /*UUID*/, val room: Int, val classId: UUID /*UUID*/,
    val subjectId: UUID /*UUID*/, val period: Int
)
