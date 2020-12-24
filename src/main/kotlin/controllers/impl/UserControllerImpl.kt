package controllers.impl

import controllers.UserController
import controllers.models.User
import dao.UserDao
import dao.models.UserDaoModel
import dao.models.UserStatusDaoModel
import dao.models.RoleDaoModel
import getLogger
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import services.UserService
import java.util.*

class UserControllerImpl(
    private val routing: Routing,
    private val userService: UserService
) : UserController {

    private fun Routing.user() {
        route("/users") {
            // http://localhost:8080/users/7b89ea87-27e8-11eb-aa2f-0242ac140002
            get("/{id}") {
                val userId = call.parameters["id"]
                userId ?: getLogger().warn("Empty path parameter.")
                call.respond(getUser(UUID.fromString(userId)))
            }

            // http://localhost:8080/schedules/class?classNumber=10&classLetter=а
            get("/{username}") {
                val queryParameters = call.request.queryParameters
                val username = queryParameters["username"]
                if (username == null) {
                    getLogger().warn("Wrong parameters. Expected 'username' type of String")
                    call.respond(HttpStatusCode.NotFound)
                } else {
                    val response = getUserByName(username)
                    call.respond(response)
                }
            }

            // http://localhost:8080/schedules/class?classNumber=10&classLetter=а
            get("/{email}") {
                val queryParameters = call.request.queryParameters
                val email = queryParameters["email"]
                if (email == null) {
                    getLogger().warn("Wrong parameters. Expected 'email' type of String")
                    call.respond(HttpStatusCode.NotFound)
                } else {
                    val response = getUserByName(email)
                    call.respond(response)
                }
            }

            // http://localhost:8080/users/status?statusId=10&classLetter=а
            get("/status") {
                val queryParameters = call.request.queryParameters
                val statusId = queryParameters["statusId"]?.toInt()
                if (statusId == null) {
                    getLogger().warn("Wrong parameters. Expected 'statusId' type of Int")
                    call.respond(HttpStatusCode.NotFound)
                } else {
                    val response = getUserStatus(statusId)
                    call.respond(response)
                }
            }

            // http://localhost:8080/users/role?userId=10&classLetter=а
            get("/role") {
                val queryParameters = call.request.queryParameters
                val userId = queryParameters["userId"]
                if (userId == null) {
                    getLogger().warn("Wrong parameters. Expected 'userId' type of Int")
                    call.respond(HttpStatusCode.NotFound)
                } else {
                    val response = getUserRole(UUID.fromString(userId))
                    call.respond(response)
                }
            }

            post("/{roleId}") {
                val user = call.receive<User>()
                val queryParameters = call.request.queryParameters
                val roleId = queryParameters["roleId"]?.toInt()
                if (roleId == null) {
                    getLogger().warn("Wrong parameters. Expected 'roleId' type of Int")
                    call.respond(HttpStatusCode.NotFound)
                } else {
                    val id = addUser(user, roleId)
                    call.respond(HttpStatusCode.Created, id)
                }
            }

            put("/{id}") {
                val userId = call.parameters["id"]
                val updatedUser = call.receive<User>()
                userId ?: getLogger().warn("Empty path parameter.")
                updateUser(UUID.fromString(userId), updatedUser)
                call.respond(HttpStatusCode.OK)
            }

            delete("/{id}") {
                val userId = call.parameters["id"]
                userId ?: getLogger().warn("Empty path parameter.")
                deleteUser(UUID.fromString(userId))
                call.respond(HttpStatusCode.Accepted)
            }
        }
    }

    fun addRouts() {
        routing.user()
    }

    override fun getUser(userId: UUID): UserDaoModel {
        return userService.getUser(userId)
    }

    override fun getUserByName(username: String): UserDaoModel {
        return userService.getUserByName(username)
    }

    override fun getUserByEmail(email: String): UserDaoModel {
        return userService.getUserByEmail(email)
    }

    override fun getUserStatus(userStatusId: Int): UserStatusDaoModel {
        return userService.getUserStatus(userStatusId)
    }

    override fun getUserRole(userId: UUID): List<RoleDaoModel> {
        return userService.getUserRole(userId)
    }

    override fun addUser(user: User, roleId: Int): UUID {
        return userService.addUser(user, roleId)
    }

    override fun updateUser(userId: UUID, User: User) {
        userService.updateUser(userId, User)
    }

    override fun deleteUser(userId: UUID) {
        userService.deleteUser(userId)
    }
}