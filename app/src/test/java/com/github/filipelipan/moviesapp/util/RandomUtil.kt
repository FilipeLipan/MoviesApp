package com.github.filipelipan.moviesapp.util

import kotlin.random.Random
import kotlin.random.nextULong

object RandomUtil {

    fun id(): Int = Random.nextInt()

    fun int(from: Int = 0, until: Int = 10): Int = Random.nextInt(from = from, until = until)

    fun double(from: Double = 0.0, until: Double = 10.0): Double =
        Random.nextDouble(from = from, until = until)

    fun boolean(): Boolean = Random.nextBoolean()

    fun name(): String = Random.nextULong().toString()

    fun fullName(): String = "${Random.nextULong().toString()} ${Random.nextULong().toString()}"

    fun <T> listOf(
        quantity: Int = int(from = 1, until = 3),
        block: () -> T,
    ) = mutableListOf<T>().apply {
        repeat(quantity) { add(block()) }
    }
}
