package dao

import services.UserService
import dao.UserDao
import dao.models.UserDaoModel
import dao.models.UserStatusDaoModel
import dao.models.RoleDaoModel
import java.util.*

interface UserDao {

    fun getUser(userId: UUID): UserDaoModel

    fun getUserByName(username: String): UserDaoModel

    fun getUserByEmail(email: String): UserDaoModel

    fun getUserStatus(userStatusId: Int): UserStatusDaoModel

    fun getUserRole(userId: UUID): List<RoleDaoModel>

    fun addUser(User: User): UUID

    fun updateUser(userId: UUID, User: User)

    fun deleteUser(userId: UUID)
}