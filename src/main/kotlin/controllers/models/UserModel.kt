package controllers.models

data class UserModel(
    val username: String,
    val email: String,
    var password: String,
    val last_name: String,
    val first_name: String,
    val middle_name: String,
    val class_id: String,
    val user_status_id: Int
)