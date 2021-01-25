package dao

import controllers.models.NewsModel
import dao.models.NewsDaoModel
import java.util.*

interface NewsDao {
    suspend fun getAllNews(): List<NewsDaoModel>

    suspend fun getNewsItem(newsId: UUID): NewsDaoModel

    suspend fun addNewsItem(newsModel: NewsModel): UUID

    suspend fun updateNewsItem(newsId: UUID, updatedNews: NewsModel)

    suspend fun deleteNewsItem(newsId: UUID)
}