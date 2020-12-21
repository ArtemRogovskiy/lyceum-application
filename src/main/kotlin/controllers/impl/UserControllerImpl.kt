package controllers.impl

import controllers.UserController
import services.UserService
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

            // http://localhost:8080/users/7b89ea87-27e8-11eb-aa2f-0242ac140002
            get("/{username}") {
                val username = call.parameters["username"]
                username ?: getLogger().warn("Empty path parameter.")
                call.respond(getUserByName(UUID.fromString(username)))
            }

            // http://localhost:8080/users/7b89ea87-27e8-11eb-aa2f-0242ac140002
            get("/{email}") {
                val email = call.parameters["email"]
                email ?: getLogger().warn("Empty path parameter.")
                call.respond(getUserByEmail(UUID.fromString(email)))
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
                val userId = queryParameters["userId"]?.toInt()
                if (userId == null) {
                    getLogger().warn("Wrong parameters. Expected 'userId' type of Int")
                    call.respond(HttpStatusCode.NotFound)
                } else {
                    val response = getUserRole(userId)
                    call.respond(response)
                }
            }

            post {
                val user = call.receive<User>()
                val id = addUser(user)
                call.respond(HttpStatusCode.Created, id)
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
        return userDao.getUser(userId)
    }

    override fun getUserByName(username: String): UserDaoModel {
        return userDao.getUserByName(username)
    }

    override fun getUserByEmail(email: String): UserDaoModel {
        return userDao.getUserByEmail(email)
    }

    override fun getUserStatus(userStatusId: Int): UserStatusDaoModel {
        return userDao.getUserStatus(userStatusId)
    }

    override fun getUserRole(userId: UUID): List<RoleDaoModel> {
        return userDao.getUserRole(userId)
    }

    override fun addUser(User: User, roleId: Int): UUID {
        return userDao.addUser(User: User, roleId: Int)
    }

    override fun updateUser(userId: UUID, User: User) {
        return userDao.updateUser(userId: UUID, User: User)
    }

    override fun deleteUser(userId: UUID) {
        return userDao.deleteUser(userId: UUID)
    }
}