import org.koin.core.context.startKoin

fun main(args: Array<String>) {
    // Koin DI
    startKoin {
        printLogger()
        modules(listOf(scheduleKoinModule, schoolClassKoinModule))
    }
    io.ktor.server.netty.EngineMain.main(args)
}