package services

import controllers.models.UserModel
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

    fun addUser(user: UserModel, roleId: Int): UUID

    fun updateUser(userId: UUID, User: UserModel)

    fun deleteUser(userId: UUID)
}