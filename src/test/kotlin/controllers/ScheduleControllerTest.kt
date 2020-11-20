package controllers

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.server.testing.*
import scheduleModule
import kotlin.test.*

class ScheduleControllerTest {

    @Test
    fun testClassScheduleKeyUnexistent(): Unit = withTestApplication(Application::scheduleModule) {
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
    fun testTeacherScheduleKeyUnexistent(): Unit = withTestApplication(Application::scheduleModule) {
        handleRequest(HttpMethod.Get, "/schedules/teacher").apply {
            assertEquals(404, response.status()?.value)
        }
        handleRequest(HttpMethod.Get, "/schedules/teacher?classNumber=10").apply {
            assertEquals(404, response.status()?.value)
        }
    }
}