package modules

import controllers.models.UserCredentialsModel
import controllers.models.UserModel
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.sessions.*
import org.koin.ktor.ext.inject
import services.UserService
import util.Log
import util.checkPassword
import util.hashPassword
import java.util.*

data class UserSession(val name: String, val roles: String) : Principal

@Suppress("unused")
fun Application.userModule() {

    val userService by inject<UserService>()

//    install(StatusPages) {
//        exception<Exception> {
//            call.response.status(HttpStatusCode.Forbidden)
//            call.respondRedirect("/errpage")
//        }
//    }

    routing() {

        post("/login") {
            val credentials = call.receive<UserCredentialsModel>()
            val username = credentials.username
            val password = credentials.password
//            println(hashPassword(password))
            val user = userService.getUserByName(username)
            val role = userService.getUserRole(UUID.fromString(user.id))[0]
            if (checkPassword(password, storedHash = user.password)) {
                call.sessions.set(UserSession(username, role.role))
                call.respond(HttpStatusCode.OK)
            } else {
                call.respond(HttpStatusCode.Unauthorized)
            }
        }

        get("/logout") {
            call.sessions.clear<UserSession>()
            call.respondRedirect("/")
        }

        authenticate {
            route("/users") {
                // http://0.0.0.0:8088/users/440eea88-4eb2-11eb-b245-0242ac180002
                get("/{id}") {

                    val userId = call.parameters["id"]
                    userId ?: Log.info("Empty path parameter.")
                    val uID = UUID.fromString(userId)
                    val response = userService.getUserById(uID)
                    call.respond(response)
                }

                // http://localhost:8088/users/username?username=username
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

                // http://localhost:8088/users/email?email=email@email.com
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

                // http://localhost:8088/users/status?userId=440eea88-4eb2-11eb-b245-0242ac180002
                get("/status") {
                    val queryParameters = call.request.queryParameters
                    val userId = queryParameters["userId"]
                    if (userId == null) {
                        Log.info("Wrong parameters. Expected 'statusId' type of Int")
                        call.respond(HttpStatusCode.NotFound)
                    } else {
                        val response = userService.getUserStatus(UUID.fromString(userId))
                        call.respond(response)
                    }
                }

                // http://localhost:8088/users/role?userId=440eea88-4eb2-11eb-b245-0242ac180002
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
                    user.password = hashPassword(user.password)
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