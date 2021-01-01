package util

import java.sql.Connection
import java.sql.DriverManager
import java.util.*


private var username = "root"
private var password = "password"
const val url = "jdbc:mysql://127.0.0.1:3307/"

fun getConnection(): Connection? {
    val connectionProps = Properties()
    connectionProps["user"] = username
    connectionProps["password"] = password
    connectionProps["useUnicode"] = "true"
    connectionProps["characterEncoding"] = "UTF-8"
    connectionProps["serverTimezone"] = "UTC"

    var connection: Connection? = null
    try {
        connection = DriverManager.getConnection(url, connectionProps)
    } catch (ex: Exception) {
        Log.error("Exception during DB connection: ", ex)
    }
    return connection
}
