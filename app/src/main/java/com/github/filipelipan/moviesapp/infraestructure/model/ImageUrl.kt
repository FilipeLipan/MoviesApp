package com.github.filipelipan.moviesapp.infraestructure.model

const val LOW_DEFINITION_IMG_URL = "https://image.tmdb.org/t/p/w500/"
const val HIGH_DEFINITION_IMG_URL = "https://image.tmdb.org/t/p/original"

sealed class ImageUrl {
    abstract val imagePath: String
    abstract val baseUrl: String

    data class HighDefinitionImage(
        override val imagePath: String,
        override val baseUrl: String = HIGH_DEFINITION_IMG_URL,
    ) : ImageUrl()

    data class LowDefinitionImage(
        override val imagePath: String,
        override val baseUrl: String = LOW_DEFINITION_IMG_URL,
    ) : ImageUrl()

    fun getFullUrl() : String {
        return baseUrl + imagePath
    }
}
