package dao

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