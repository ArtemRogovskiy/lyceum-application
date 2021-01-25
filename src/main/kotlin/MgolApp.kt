import org.koin.core.context.startKoin

fun main(args: Array<String>) {
    // Koin DI
    startKoin {
        printLogger()
        modules(listOf(baseKoinModule))
    }
    io.ktor.server.netty.EngineMain.main(args)
}