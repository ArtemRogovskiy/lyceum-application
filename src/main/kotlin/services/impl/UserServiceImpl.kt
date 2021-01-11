package services.impl

import services.UserService
import controllers.models.UserModel
import dao.UserDao
import dao.models.UserDaoModel
import dao.models.UserStatusDaoModel
import dao.models.RoleDaoModel
import java.util.*

class UserServiceImpl(private val userDao: UserDao) : UserService {

    override suspend fun getUser(userId: UUID): UserDaoModel {
        return userDao.getUser(userId)
    }

    override suspend fun getUserByName(username: String): UserDaoModel {
        return userDao.getUserByName(username)
    }

    override suspend fun getUserByEmail(email: String): UserDaoModel {
        return userDao.getUserByEmail(email)
    }

    override suspend fun getUserStatus(userStatusId: Int): UserStatusDaoModel {
        return userDao.getUserStatus(userStatusId)
    }

    override suspend fun getUserRole(userId: UUID): List<RoleDaoModel> {
        return userDao.getUserRole(userId)
    }

    override suspend fun addUser(user: UserModel, roleId: Int): UUID {
        return userDao.addUser(user, roleId)
    }

    override suspend fun updateUser(userId: UUID, user: UserModel) {
        userDao.updateUser(userId, user)
    }

    override suspend fun deleteUser(userId: UUID) {
        userDao.deleteUser(userId)
    }
}