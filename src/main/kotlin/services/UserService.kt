package services

import controllers.models.User
import dao.UserDao
import dao.models.UserDaoModel
import dao.models.UserStatusDaoModel
import dao.models.RoleDaoModel
import java.util.*

interface UserService {

    fun getUser(userId: UUID): UserDaoModel

    fun getUserByName(username: String): UserDaoModel

    fun getUserByEmail(email: String): UserDaoModel

    fun getUserStatus(userStatusId: Int): UserStatusDaoModel

    fun getUserRole(userId: UUID): List<RoleDaoModel>

    fun addUser(user: User, roleId: Int): UUID

    fun updateUser(userId: UUID, User: User)

    fun deleteUser(userId: UUID)
}