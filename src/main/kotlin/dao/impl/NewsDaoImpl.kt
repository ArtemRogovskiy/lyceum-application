package dao.impl

import controllers.models.NewsModel
import dao.NewsDao
import dao.models.NewsDaoModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import util.*
import java.util.*

class NewsDaoImpl : NewsDao {
    override suspend fun getAllNews(): List<NewsDaoModel> {
        val query = """
            select id, created_by, title, message, approver, create_time, is_approved, notification_type_id 
                from mgol.notification;
        """.trimIndent()
        return withContext(Dispatchers.Default) {
            executeQuery(query, NewsDaoModel.newsFromResultSet)
        }
    }

    override suspend fun getNewsItem(newsId: UUID): NewsDaoModel {
        val query = """
            select id, created_by, title, message, approver, create_time, is_approved, notification_type_id 
                from mgol.notification
                where id = '$newsId';
        """.trimIndent()
        return withContext(Dispatchers.Default) {
            executeQuery(query, NewsDaoModel.newsFromResultSet)[0]
        }
    }

    override suspend fun addNewsItem(newsModel: NewsModel): UUID {
        val id = UUID.randomUUID()
        val title = convertStringToValidString(newsModel.title)
        val message = convertStringToValidString(newsModel.message)
        val prover = convertUUIDToValidString(newsModel.prover)
        val isApproved = if (newsModel.isApproved) 1 else 0
        val query = """
            insert into mgol.notification
                (id, created_by, title, message, approver, is_approved, notification_type_id) 
                values('$id', '${newsModel.createdBy}', $title, $message, $prover, 
                $isApproved, '${newsModel.notificationTypeId}');
        """.trimIndent()
        return withContext(Dispatchers.Default) {
            executeUpdate(query)
            id
        }
    }

    override suspend fun updateNewsItem(newsId: UUID, updatedNews: NewsModel) {
        val title = convertStringToValidString(updatedNews.title)
        val message = convertStringToValidString(updatedNews.message)
        val prover = convertUUIDToValidString(updatedNews.prover)
        val isApproved = if (updatedNews.isApproved) 1 else 0
        val query = """
            update mgol.notification
                set created_by = '${updatedNews.createdBy}', 
                title = $title, 
                message = $message, 
                approver = $prover,
                is_approved = $isApproved,
                notification_type_id = '${updatedNews.notificationTypeId}'
                where id = '$newsId';
        """.trimIndent()
        withContext(Dispatchers.Default) {
            val rowsNum = executeUpdate(query)
            Log.info("$rowsNum rows have been updated in mgol.notification")
        }
    }

    override suspend fun deleteNewsItem(newsId: UUID) {
        val query = """
            delete from mgol.notification where id = '$newsId';
        """.trimIndent()
        withContext(Dispatchers.Default) {
            val rowsNum = executeUpdate(query)
            Log.info("$rowsNum rows have been deleted from mgol.notification")
        }
    }
}