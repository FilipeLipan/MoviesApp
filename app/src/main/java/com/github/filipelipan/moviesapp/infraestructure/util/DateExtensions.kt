package com.github.filipelipan.moviesapp.infraestructure.util

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

const val DEFAULT_DATE_PATTERN = "yyyy-MM-dd"

fun String.parseDateDefault() : Date? {
    return try {
        val dateFormatter =  SimpleDateFormat(DEFAULT_DATE_PATTERN, Locale.getDefault())
        dateFormatter.parse(this)
    } catch (parseException: ParseException) {
        null
    }
}

fun Date.formatDate(
    pattern: String
) : String? {
    return try {
        val dateFormatter =  SimpleDateFormat(pattern, Locale.getDefault())
        return dateFormatter.format(this)
    } catch (parseException: ParseException) {
        null
    }
}
