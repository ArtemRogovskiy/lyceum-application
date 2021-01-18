package modules

import controllers.models.UserModel
import org.koin.ktor.ext.inject

import dao.UserDao
import dao.models.UserDaoModel
import dao.models.UserStatusDaoModel
import dao.models.RoleDaoModel
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import services.UserService
import services.SchoolClassService
import util.Log
import java.util.*
import io.ktor.auth.*
import io.ktor.features.*
import io.ktor.http.content.*
import io.ktor.sessions.*

data class UserSession(val name: String, val roles: String) : Principal
data class OriginalRequestURI(val uri: String)

@Suppress("unused") // Referenced in application.conf
fun Application.userModule() {
    val userService by inject<UserService>()

    install(StatusPages) {
        exception<Exception> {
            call.response.status(HttpStatusCode.Forbidden)
            call.respondRedirect("/errpage")
        }
    }

    install(Sessions) {
        cookie<UserSession>("ktor_session_cookie", SessionStorageMemory())
        cookie<OriginalRequestURI>("original_request_cookie")
    }

    install(Authentication) {

        session<UserSession> {
            challenge {
                call.sessions.set(OriginalRequestURI(call.request.uri))
                call.respondRedirect("/login")
            }
            validate { session: UserSession ->
                session
            }
        }

    }

    routing() {

        post("/login") {
            val queryParameters = call.request.queryParameters
            val username = queryParameters["username"]
            val password = queryParameters["password"]
            if (username != null && password != null) {
                val user = userService.getUserByName(username)
                val role = userService.getUserRole(UUID.fromString(user.id))[0]
                if (user.password == password) {
                    call.sessions.set(UserSession(username, role.name))
                    val redirectURL = call.sessions.get<OriginalRequestURI>()?.also {
                        call.sessions.clear<OriginalRequestURI>()
                    }
                    call.respondRedirect(redirectURL?.uri ?: "/")
                } else {
                    call.respondRedirect("/users/login")
                }
            } else {
                call.respondRedirect("/users/login")
            }
        }

        get("/logout") {
            call.sessions.clear<UserSession>()
            call.respondRedirect("/")
        }

        authenticate {
            route("/users") {
                // http://0.0.0.0:8080/users/440eea88-4eb2-11eb-b245-0242ac180002
                get("/{id}") {

                    val userId = call.parameters["id"]
                    userId ?: Log.info("Empty path parameter.")
                    val uID = UUID.fromString(userId)
                    val response = userService.getUser(uID)
                    call.respond(response)
                }

                // http://localhost:8080/users/username?username=username
                get("/username") {
                    val queryParameters = call.request.queryParameters
                    val username = queryParameters["username"]?.toLowerCase()
                    if (username == null) {
                        Log.warn("Wrong parameters. Expected 'username' type of String")
                        call.respond(HttpStatusCode.NotFound)
                    } else {
                        val response = userService.getUserByName(username)
                        call.respond(response)
                    }
                }

                // http://localhost:8080/users/email?email=email@email.com
                get("/email") {
                    val queryParameters = call.request.queryParameters
                    val email = queryParameters["email"]?.toLowerCase()
                    if (email == null) {
                        Log.info("Wrong parameters. Expected 'email' type of String")
                        call.respond(HttpStatusCode.NotFound)
                    } else {
                        val response = userService.getUserByEmail(email)
                        call.respond(response)
                    }
                }

                // http://localhost:8080/users/status?userId=440eea88-4eb2-11eb-b245-0242ac180002
                get("/status") {
                    val queryParameters = call.request.queryParameters
                    val statusId = queryParameters["userId"]?.toInt()
                    if (statusId == null) {
                        Log.info("Wrong parameters. Expected 'statusId' type of Int")
                        call.respond(HttpStatusCode.NotFound)
                    } else {
                        val response = userService.getUserStatus(statusId)
                        call.respond(response)
                    }
                }

                // http://localhost:8080/users/role?userId=440eea88-4eb2-11eb-b245-0242ac180002
                get("/role") {
                    val queryParameters = call.request.queryParameters
                    val userId = queryParameters["userId"]
                    if (userId == null) {
                        Log.info("Wrong parameters. Expected 'userId' type of Int")
                        call.respond(HttpStatusCode.NotFound)
                    } else {
                        val response = userService.getUserRole(UUID.fromString(userId))
                        call.respond(response)
                    }
                }

                post("/{roleId}") {
                    val user = call.receive<UserModel>()
                    val queryParameters = call.request.queryParameters
                    val roleId = queryParameters["roleId"]?.toInt()
                    if (roleId == null) {
                        Log.info("Wrong parameters. Expected 'roleId' type of Int")
                        call.respond(HttpStatusCode.NotFound)
                    } else {
                        val id = userService.addUser(user, roleId)
                        call.respond(HttpStatusCode.Created, id)
                    }
                }

                put("/{id}") {
                    val userId = call.parameters["id"]
                    val updatedUser = call.receive<UserModel>()
                    userId ?: Log.info("Empty path parameter.")
                    userService.updateUser(UUID.fromString(userId), updatedUser)
                    call.respond(HttpStatusCode.OK)
                }

                delete("/{id}") {
                    val userId = call.parameters["id"]
                    userId ?: Log.info("Empty path parameter.")
                    userService.deleteUser(UUID.fromString(userId))
                    call.respond(HttpStatusCode.Accepted)
                }
            }
        }
    }

}