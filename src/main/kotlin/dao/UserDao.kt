package dao

import controllers.models.UserModel
import dao.models.UserDaoModel
import dao.models.UserStatusDaoModel
import dao.models.RoleDaoModel
import java.util.*

interface UserDao {

   suspend fun getUser(userId: UUID): UserDaoModel

   suspend fun getUserByName(username: String): UserDaoModel

   suspend fun getUserByEmail(email: String): UserDaoModel

   suspend fun getUserByClass(classId: String): UserDaoModel

   suspend fun getUserStatus(userStatusId: Int): UserStatusDaoModel

   suspend fun getUserRole(userId: UUID): List<RoleDaoModel>

   suspend fun addUser(user: UserModel, roleId: Int): UUID

   suspend fun updateUser(userId: UUID, User: UserModel)

   suspend fun deleteUser(userId: UUID)
}