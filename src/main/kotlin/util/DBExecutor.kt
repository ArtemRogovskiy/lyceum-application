package main.kotlin.util

import getLogger
import java.sql.ResultSet

fun <T> executeQuery(query: String, rowMapper: (ResultSet) -> List<T>): List<T> {
    var list: List<T> = listOf()
    getConnection().use { connection ->
        connection?.createStatement().use { statement ->
            statement?.executeQuery(query).use { resultSet ->
                if (resultSet == null) {
                    getLogger().warn("Result set is null after execution of $query")
                } else {
                    list = rowMapper(resultSet)
                }
            }
        }
    }
    return list
}


