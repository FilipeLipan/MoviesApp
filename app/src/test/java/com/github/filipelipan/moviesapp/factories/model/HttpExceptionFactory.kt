package com.github.filipelipan.moviesapp.factories.model

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response

object HttpExceptionFactory {
    fun make(
        code: Int = 400,
    ) = HttpException(
        Response.error<ResponseBody>(
            code,
            ResponseBody.create("plain/text".toMediaType(),
                ""
            )
        )
    )
}
