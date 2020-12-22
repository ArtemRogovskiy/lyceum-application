package services.impl

import controllers.models.NewsModel
import dao.NewsDao
import dao.models.NewsDaoModel
import services.NewsService
import java.util.*

class NewsServiceImpl(private val newsDao: NewsDao) : NewsService {
    override suspend fun getAllNews(): List<NewsDaoModel> {
        return newsDao.getAllNews()
    }

    override suspend fun getNewsItem(newsId: UUID): NewsDaoModel {
        return newsDao.getNewsItem(newsId)
    }

    override suspend fun addNewsItem(newsModel: NewsModel): UUID {
        return newsDao.addNewsItem(newsModel)
    }

    override suspend fun updateNewsItem(newsId: UUID, updatedNews: NewsModel) {
        newsDao.updateNewsItem(newsId, updatedNews)
    }

    override suspend fun deleteNewsItem(newsId: UUID) {
        newsDao.deleteNewsItem(newsId)
    }

}