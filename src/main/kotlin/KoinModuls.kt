import dao.NewsDao
import dao.ScheduleDao
import dao.SchoolClassDao
import dao.impl.NewsDaoImpl
import dao.impl.ScheduleDaoImpl
import dao.impl.SchoolClassDaoImpl
import org.koin.dsl.module
import org.koin.experimental.builder.singleBy
import services.NewsService
import services.ScheduleService
import services.SchoolClassService
import services.impl.NewsServiceImpl
import services.impl.ScheduleServiceImpl
import services.impl.SchoolClassServiceImpl

val baseKoinModule = module {
    singleBy<ScheduleService, ScheduleServiceImpl>()
    singleBy<ScheduleDao, ScheduleDaoImpl>()

    singleBy<SchoolClassService, SchoolClassServiceImpl>()
    singleBy<SchoolClassDao, SchoolClassDaoImpl>()

    singleBy<NewsService, NewsServiceImpl>()
    singleBy<NewsDao, NewsDaoImpl>()
}
