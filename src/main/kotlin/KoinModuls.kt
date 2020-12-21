import dao.ScheduleDao
import dao.SchoolClassDao
import dao.impl.ScheduleDaoImpl
import dao.impl.SchoolClassDaoImpl
import org.koin.dsl.module
import org.koin.experimental.builder.singleBy
import services.ScheduleService
import services.SchoolClassService
import services.impl.ScheduleServiceImpl
import services.impl.SchoolClassServiceImpl

val scheduleKoinModule = module {
    singleBy<ScheduleService, ScheduleServiceImpl>()
    singleBy<ScheduleDao, ScheduleDaoImpl>()
}

val schoolClassKoinModule = module {
    singleBy<SchoolClassService, SchoolClassServiceImpl>()
    singleBy<SchoolClassDao, SchoolClassDaoImpl>()
}
