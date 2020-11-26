import controllers.models.ClassSchedule
import dao.models.ClassScheduleDaoModel
import dao.models.ScheduleDaoModel
import dao.models.TeacherScheduleDaoModel
import java.util.*

val scheduleId: UUID = UUID.fromString("7b89ea87-27e8-11eb-aa2f-0242ac140002")
val teacherId: UUID = UUID.fromString("6e5cd906-27e8-11eb-aa2f-0242ac140002")
val classId: UUID = UUID.fromString("660ef080-27e8-11eb-aa2f-0242ac140002")
val subjectId: UUID = UUID.fromString("4e7e0f73-27e8-11eb-aa2f-0242ac140002")

val scheduleDaoModel = ScheduleDaoModel(
    "7b89ea87-27e8-11eb-aa2f-0242ac140002",
    "6e5cd906-27e8-11eb-aa2f-0242ac140002",
    "21",
    "660ef080-27e8-11eb-aa2f-0242ac140002",
    "4e7e0f73-27e8-11eb-aa2f-0242ac140002",
    30
)

val listOfClassScheduleDaoModels = listOf(
    ClassScheduleDaoModel(
        0,
        "09:55:00",
        "10:40:00",
        "Математика",
        314,
        "Иванов",
        "Иван",
        "Иванович"
    ),
    ClassScheduleDaoModel(
        1,
        "09:00:00",
        "09:45:00",
        "Физика",
        272,
        "Иванов",
        "Иван",
        "Иванович"
    )
)

val listOfTeacherScheduleDaoModels = listOf(
    TeacherScheduleDaoModel(
        0,
        "09:55:00",
        "10:40:00",
        "Математика",
        314,
        10,
        "А"
    ),
    TeacherScheduleDaoModel(
        0,
        "11:00:00",
        "11:45:00",
        "Математика",
        314,
        10,
        "Б"
    )
)

val classSchedule = ClassSchedule(
    teacherId,
    1,
    classId,
    subjectId,
    30
)

const val schedulePostBody =
    "{\"teacherId\": \"6e5cd906-27e8-11eb-aa2f-0242ac140002\", \"room\": 1, \"classId\": " +
            "\"660ef080-27e8-11eb-aa2f-0242ac140002\", \"subjectId\": " +
            "\"4e7e0f73-27e8-11eb-aa2f-0242ac140002\", \"period\": 30}"

const val schedulePutBody =
    "{\"teacherId\": \"6e5cd906-27e8-11eb-aa2f-0242ac140002\", \"room\": 1, " +
            "\"classId\": \"660ef080-27e8-11eb-aa2f-0242ac140002\", " +
            "\"subjectId\": \"4e7e0f73-27e8-11eb-aa2f-0242ac140002\", \"period\": 30}"