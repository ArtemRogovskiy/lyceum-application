import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.core.context.startKoin

fun main(args: Array<String>) {
    // Koin DI
    startKoin {
        printLogger()
        modules(listOf(scheduleKoinModule))
    }
    io.ktor.server.netty.EngineMain.main(args)
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