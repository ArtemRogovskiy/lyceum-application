package main.kotlin.util

import java.sql.Connection
import java.sql.DriverManager
import java.util.*


private var username = "mgol"
private var password = "mgol1996"
val url = "jdbc:mysql://127.0.0.1:3306/"

fun getConnection(): Connection? {
    val connectionProps = Properties()
    connectionProps["user"] = username
    connectionProps["password"] = password
    connectionProps["useUnicode"] = "true"
    connectionProps["characterEncoding"] = "UTF-8"
    connectionProps["serverTimezone"] = "UTC"

    var connection: Connection? = null
    try {
        connection = DriverManager.getConnection(
            url, connectionProps
        )
    } catch (ex: Exception) {
        ex.printStackTrace()
    }
    return connection;
}
