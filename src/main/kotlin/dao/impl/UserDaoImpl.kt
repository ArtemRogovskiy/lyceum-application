package dao.impl

import dao.UserDao

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
        return executeQuery(query, UserStatusDaoModel.userFromResultSet)[0]
    }

    override fun getUserRole(userId: UUID): List<RoleDaoModel> {
        val query =
            "select r.name from mgol.role r join mgol.user_role ur" +
                    "on r.id = ur.role_id join mgol.user u" +
                    "where ur.user_id = '$userId';"
        return executeQuery(query, UserStatusDaoModel.userFromResultSet)[0]
    }

    override fun addUser(User: User, roleId: Int): UUID {
        val id = UUID.randomUUID()
        val query = "insert into mgol.user " +
                "(id, username, email, password last_name, first_name, middle_name, class_id, user_status_id) values('$id', " +
                "'${User.username}', ${User.email}, '${User.password}', " +
                "'${User.last_name}', ${User.first_name}, '${User.middle_name}', " +
                "'${User.class_id}', ${User.user_status_id});"
        executeUpdate(query)
        val query = "insert into mgol.user_role " +
                "(user_id, role_id) values('$id', " + "'$roleId');"
        executeUpdate(query)
        return id
    }

    override fun updateUser(userId: UUID, User: User) {
        val query = "update mgol.user " +
                "set username = '${User.username}', email = ${User.email}, " +
                "password = '${User.password}', last_name = '${User.last_name}', " +
                "first_name = '${User.first_name}', middle_name = '${User.middle_name}', " +
                "class_id = '${User.class_id}', user_status_id = '${User.user_status_id}', " +
                "where id = '$userId';"
        val rowsNum = executeUpdate(query)
        getLogger().info("$rowsNum rows have been updated")
    }

    override fun deleteUser(userId: UUID) {
        val query = "delete from mgol.user where id = '$userId';"
        val rowsNum = executeUpdate(query)
        val query = "delete from mgol.user_role where user_id = '$userId';"
        val rowsNum += executeUpdate(query)
        getLogger().info("$rowsNum rows have been deleted")
    }
}