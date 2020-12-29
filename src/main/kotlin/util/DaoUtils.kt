package util

import java.util.*

fun convertUUIDToValidString(id: UUID?): String? {
    return if (id != null) {
        "'${id}'"
    } else {
        null
    }
}

fun convertStringToValidString(str: String?): String? {
    return if (str != null) {
        "'${str}'"
    } else {
        null
    }
}