package controllers

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.server.testing.*
import scheduleModule
import kotlin.test.*

class ScheduleControllerTest {

//    private val registrationController: RegistrationController = mockk()

    @Test
    fun testClassScheduleKeyUnexistent(): Unit = withTestApplication(Application::scheduleModule) {
        handleRequest(HttpMethod.Get, "/schedule/class?classNumber=10").apply {
            assertEquals(404, response.status()?.value)
        }
        handleRequest(HttpMethod.Get, "/schedule/class?classLetter=a").apply {
            assertEquals(404, response.status()?.value)
        }
        handleRequest(HttpMethod.Get, "/schedule/class").apply {
            assertEquals(404, response.status()?.value)
        }
    }

    @Test
    fun testTeacherScheduleKeyUnexistent(): Unit = withTestApplication(Application::scheduleModule) {
        handleRequest(HttpMethod.Get, "/schedule/teacher").apply {
            assertEquals(404, response.status()?.value)
        }
        handleRequest(HttpMethod.Get, "/schedule/teacher?classNumber=10").apply {
            assertEquals(404, response.status()?.value)
        }
    }
}