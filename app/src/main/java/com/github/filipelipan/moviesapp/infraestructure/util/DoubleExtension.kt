package com.github.filipelipan.moviesapp.infraestructure.util

import java.math.RoundingMode

fun Double.formatRating() : String {
    val decimal = this.toBigDecimal().setScale(1, RoundingMode.HALF_EVEN)
    return decimal.toString()
}
