package dao.impl

import services.UserService
import dao.UserDao
import controllers.models.UserModel
import dao.models.UserDaoModel
import dao.models.UserStatusDaoModel
import dao.models.RoleDaoModel
import util.executeQuery
import util.executeUpdate
import util.Log
import java.util.*

class UserDaoImpl : UserDao {

    override fun getUser(userId: UUID): UserDaoModel {
        val query =
            "select id, username, email, last_name, first_name," +
                    "middle_name, create_time, class_id, user_status_id" +
                    "from mgol.user " +
                    "where id = '$userId';"
        return executeQuery(query, UserDaoModel.userFromResultSet)[0]
    }

    override fun getUserByName(username: String): UserDaoModel {
        val query =
            "select id, username, email, last_name, first_name," +
                    "middle_name, create_time, class_id, user_status_id" +
                    "from mgol.user " +
                    "where username = '$username';"
        return executeQuery(query, UserDaoModel.userFromResultSet)[0]
    }

    override fun getUserByClass(classId: String): UserDaoModel {
        val query =
            "select id, username, email, last_name, first_name," +
                    "middle_name, create_time, class_id, user_status_id" +
                    "from mgol.user " +
                    "where class_id = '$classId';"
        return executeQuery(query, UserDaoModel.userFromResultSet)[0]
    }

    override fun getUserByEmail(email: String): UserDaoModel {
        val query =
            "select id, username, email, last_name, first_name," +
                    "middle_name, create_time, class_id, user_status_id" +
                    "from mgol.user " +
                    "where email = '$email';"
        return executeQuery(query, UserDaoModel.userFromResultSet)[0]
    }

    override fun getUserStatus(userId: Int): UserStatusDaoModel {
        val query =
            "select us.name from mgol.user_status us join mgol.user u" +
                    "on us.id = u.user_status_id" +
                    "where u.id = '$userId';"
        return executeQuery(query, UserStatusDaoModel.userStatusFromResultSet)[0]
    }

    override fun getUserRole(userId: UUID): List<RoleDaoModel> {
        val query =
            "select r.name from mgol.role r join mgol.user_role ur" +
                    "on r.id = ur.role_id join mgol.user u" +
                    "where ur.user_id = '$userId';"
        return executeQuery(query, RoleDaoModel.rolesFromResultSet)
    }

    override fun addUser(user: UserModel, roleId: Int): UUID {
        val id = UUID.randomUUID()
        val queryUser = "insert into mgol.user " +
                "(id, username, email, password last_name, first_name, middle_name, class_id, user_status_id) values('$id', " +
                "'${user.username}', ${user.email}, '${user.password}', " +
                "'${user.last_name}', ${user.first_name}, '${user.middle_name}', " +
                "'${user.class_id}', ${user.user_status_id});"
        executeUpdate(queryUser)
        val queryRole = "insert into mgol.user_role " +
                "(user_id, role_id) values('$id', " + "'$roleId');"
        executeUpdate(queryRole)
        return id
    }

    override fun updateUser(userId: UUID, User: UserModel) {
        val query = "update mgol.user " +
                "set username = '${User.username}', email = ${User.email}, " +
                "password = '${User.password}', last_name = '${User.last_name}', " +
                "first_name = '${User.first_name}', middle_name = '${User.middle_name}', " +
                "class_id = '${User.class_id}', user_status_id = '${User.user_status_id}', " +
                "where id = '$userId';"
        val rowsNum = executeUpdate(query)
        Log.info("$rowsNum rows have been updated")
    }

    override fun deleteUser(userId: UUID) {
        val queryUser = "delete from mgol.user where id = '$userId';"
        val queryRole = "delete from mgol.user_role where user_id = '$userId';"
        val rowsNumUser = executeUpdate(queryUser)
        val rowsNumRole = executeUpdate(queryRole)
        Log.info("$rowsNumUser rows have been deleted from User")
        Log.info("$rowsNumRole rows have been deleted from UserRole")
    }
}