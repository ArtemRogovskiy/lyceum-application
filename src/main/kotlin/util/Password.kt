package util

import org.mindrot.jbcrypt.BCrypt

private const val workload = 12

fun hashPassword(passwordPlainTest: String): String {
    val salt = BCrypt.gensalt(workload)
    return BCrypt.hashpw(passwordPlainTest, salt)
}

fun checkPassword(passwordPlainText: String, storedHash: String): Boolean {
    require(storedHash.startsWith("$2a$")) { "Invalid hash provided for comparison" }
    return BCrypt.checkpw(passwordPlainText, storedHash)
}