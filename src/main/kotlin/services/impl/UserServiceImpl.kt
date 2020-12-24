package services.impl

import services.UserService
import controllers.models.User
import dao.UserDao
import dao.models.UserDaoModel
import dao.models.UserStatusDaoModel
import dao.models.RoleDaoModel
import java.util.*

class UserServiceImpl(private val userDao: UserDao) : UserService {

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

    override fun addUser(user: User, roleId: Int): UUID {
        return userDao.addUser(user, roleId)
    }

    override fun updateUser(userId: UUID, user: User) {
        userDao.updateUser(userId, user)
    }

    override fun deleteUser(userId: UUID) {
        userDao.deleteUser(userId)
    }
}