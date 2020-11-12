import controllers.impl.ScheduleControllerImpl
import dao.impl.ScheduleDaoImpl
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.Compression
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.gson.*
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import services.impl.ScheduleServiceImpl
import java.text.DateFormat

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.scheduleModule() {
    install(DefaultHeaders)
    install(Compression)
    install(CallLogging)
    install(ContentNegotiation) {
        gson {
            setDateFormat(DateFormat.LONG)
            setPrettyPrinting()
        }
    }
    val scheduleService = ScheduleServiceImpl(ScheduleDaoImpl())
    routing {
        ScheduleControllerImpl(this, scheduleService).addRouts()
    }
}

// Module for UserController
@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.userModule() {
    routing {
        get("/user") {
            call.respondText("Hello World!")
        }
    }
}