package modules

import controllers.models.NewsModel
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject
import services.NewsService
import util.Log
import java.util.*

@Suppress("unused") // Referenced in application.conf
fun Application.newsModule() {
    val newsService by inject<NewsService>()

    routing {
        route("/news") {
            // http://localhost:8080/news
            get() {
                call.respond(newsService.getAllNews())
            }

            // http://localhost:8080/news/6e5cd906-27e8-11eb-aa2f-0242ac140002
            get("/{newsId}") {
                val newsId = call.parameters["newsId"]
                newsId ?: Log.warn("Empty path parameter: newsId")
                val response = newsService.getNewsItem(UUID.fromString(newsId))
                call.respond(response)
            }

            post {
                val newsItem = call.receive<NewsModel>()
                val id = newsService.addNewsItem(newsItem)
                call.respond(HttpStatusCode.Created, id)
            }

            put("/{newsId}") {
                val newsId = call.parameters["newsId"]
                val updatedNews = call.receive<NewsModel>()
                newsId ?: Log.warn("Empty path parameter: newsId")
                newsService.updateNewsItem(UUID.fromString(newsId), updatedNews)
                call.respond(HttpStatusCode.OK)
            }

            delete("/{newsId}") {
                val newsId = call.parameters["newsId"]
                newsId ?: Log.warn("Empty path parameter: newsId")
                newsService.deleteNewsItem(UUID.fromString(newsId))
                call.respond(HttpStatusCode.Accepted)
            }
        }
    }
}