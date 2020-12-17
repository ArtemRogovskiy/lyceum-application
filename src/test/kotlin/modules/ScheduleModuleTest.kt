package modules

import classSchedule
import com.google.gson.Gson
import dao.ScheduleDao
import dao.models.ClassScheduleDaoModel
import dao.models.ScheduleDaoModel
import dao.models.TeacherScheduleDaoModel
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.server.testing.*
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import listOfClassScheduleDaoModels
import listOfTeacherScheduleDaoModels
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.mock.MockProviderRule
import org.koin.test.mock.declareMock
import scheduleDaoModel
import scheduleId
import scheduleKoinModule
import schedulePostBody
import schedulePutBody
import teacherId
import java.util.*
import kotlin.test.assertEquals

class ScheduleModuleTest : KoinTest {

    private var gson = Gson()

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        printLogger()
        modules(scheduleKoinModule)
    }

    @get:Rule
    val mockProvider = MockProviderRule.create {
        mockk(moreInterfaces = arrayOf(it))
    }

    @Test
    fun `getSchedule route test OK status`(): Unit = withTestApplication(Application::scheduleModule) {
        val mockScheduleDao = declareMock<ScheduleDao> {
            coEvery { getSchedule(scheduleId) } returns ScheduleDaoModel(
                "7b89ea87-27e8-11eb-aa2f-0242ac140002",
                "6e5cd906-27e8-11eb-aa2f-0242ac140002",
                "21",
                "660ef080-27e8-11eb-aa2f-0242ac140002",
                "4e7e0f73-27e8-11eb-aa2f-0242ac140002",
                30
            )
        }
        handleRequest(HttpMethod.Get, "/schedules/7b89ea87-27e8-11eb-aa2f-0242ac140002").apply {
            assertEquals(200, response.status()?.value)
            assertEquals(scheduleDaoModel, gson.fromJson(response.content, ScheduleDaoModel::class.java))
        }

        coVerify {
            mockScheduleDao.getSchedule(scheduleId)
        }
    }

    @Test
    fun `getClassSchedule route test OK status`(): Unit = withTestApplication(Application::scheduleModule) {
        val mockScheduleDao = declareMock<ScheduleDao> {
            coEvery { getClassSchedule(10, "А") } returns listOf(
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
        }
        handleRequest(HttpMethod.Get, "/schedules/class?classNumber=10&classLetter=а").apply {
            assertEquals(200, response.status()?.value)
            assertEquals(
                listOfClassScheduleDaoModels,
                gson.fromJson(response.content, Array<ClassScheduleDaoModel>::class.java).asList()
            )
        }

        coVerify {
            mockScheduleDao.getClassSchedule(10, "А")
        }
    }

    @Test
    fun `getClassSchedule route test 404 status`(): Unit = withTestApplication(Application::scheduleModule) {
        handleRequest(HttpMethod.Get, "/schedules/class?classNumber=10").apply {
            assertEquals(404, response.status()?.value)
        }
        handleRequest(HttpMethod.Get, "/schedules/class?classLetter=a").apply {
            assertEquals(404, response.status()?.value)
        }
        handleRequest(HttpMethod.Get, "/schedules/class").apply {
            assertEquals(404, response.status()?.value)
        }
    }

    @Test
    fun `getTeacherSchedule route test OK status`(): Unit = withTestApplication(Application::scheduleModule) {
        val mockScheduleDao = declareMock<ScheduleDao> {
            coEvery { getTeacherSchedule(teacherId) } returns listOf(
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
        }
        handleRequest(HttpMethod.Get, "/schedules/teacher?teacherId=6e5cd906-27e8-11eb-aa2f-0242ac140002").apply {
            assertEquals(200, response.status()?.value)
            assertEquals(
                listOfTeacherScheduleDaoModels,
                gson.fromJson(response.content, Array<TeacherScheduleDaoModel>::class.java).asList()
            )
        }

        coVerify {
            mockScheduleDao.getTeacherSchedule(teacherId)
        }
    }

    @Test
    fun `getTeacherSchedule route test 404 status`(): Unit = withTestApplication(Application::scheduleModule) {
        handleRequest(HttpMethod.Get, "/schedules/teacher").apply {
            assertEquals(404, response.status()?.value)
        }
        handleRequest(HttpMethod.Get, "/schedules/teacher?classNumber=10").apply {
            assertEquals(404, response.status()?.value)
        }
    }

    @Test
    fun `addSchedule route test CREATED status`(): Unit = withTestApplication(Application::scheduleModule) {
        val mockScheduleDao = declareMock<ScheduleDao> {
            coEvery { addSchedule(classSchedule) } returns scheduleId
        }
        handleRequest(HttpMethod.Post, "/schedules") {
            addHeader("content-type", "application/json")
            setBody(schedulePostBody)
        }.apply {
            assertEquals(201, response.status()?.value)
            assertEquals(scheduleId, gson.fromJson(response.content, UUID::class.java))
        }

        coVerify {
            mockScheduleDao.addSchedule(classSchedule)
        }
    }

    @Test
    fun `updateSchedule route test OK status`(): Unit = withTestApplication(Application::scheduleModule) {
        val mockScheduleDao = declareMock<ScheduleDao> {
            coEvery { updateSchedule(scheduleId, classSchedule) } returns Unit
        }
        handleRequest(HttpMethod.Put, "/schedules/7b89ea87-27e8-11eb-aa2f-0242ac140002") {
            addHeader("content-type", "application/json")
            setBody(schedulePutBody)
        }.apply {
            assertEquals(200, response.status()?.value)
        }

        coVerify {
            mockScheduleDao.updateSchedule(scheduleId, classSchedule)
        }
    }

    @Test
    fun `deleteSchedule route test Accepted status`(): Unit = withTestApplication(Application::scheduleModule) {
        val mockScheduleDao = declareMock<ScheduleDao> {
            coEvery { deleteSchedule(scheduleId) } returns Unit
        }
        handleRequest(HttpMethod.Delete, "/schedules/7b89ea87-27e8-11eb-aa2f-0242ac140002").apply {
            assertEquals(202, response.status()?.value)
        }

        coVerify {
            mockScheduleDao.deleteSchedule(scheduleId)
        }
    }
}