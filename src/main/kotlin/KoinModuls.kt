import dao.ScheduleDao
import dao.impl.ScheduleDaoImpl
import org.koin.dsl.module
import org.koin.experimental.builder.singleBy
import services.ScheduleService
import services.impl.ScheduleServiceImpl

val scheduleKoinModule = module {
    singleBy<ScheduleService, ScheduleServiceImpl>()
    singleBy<ScheduleDao, ScheduleDaoImpl>()
}
