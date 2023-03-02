package com.github.filipelipan.moviesapp.infraestructure.coroutines

import com.github.filipelipan.moviesapp.infraestructure.model.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response

@SuppressWarnings("InstanceOfCheckForException", "TooGenericExceptionCaught")
suspend fun <T> safeApiCall(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    apiCall: suspend () -> T,
): Result<T, NetworkError> {
    return withContext(dispatcher) {
        try {
            mapResponse(apiCall())
        } catch (exception: Exception) {
            if (exception is HttpException) {
                mapHttpExceptionToResultError(
                    exception = exception,
                    statusCode = exception.code(),
                    message = exception.message()
                )
            } else {
                mapGenericExceptionToResultError(exception)
            }
        }
    }
}

private fun mapHttpExceptionToResultError(
    exception: Exception? = null,
    statusCode: Int,
    message: String,
) = Result.Error(NetworkError(
    code = statusCode,
    message = message,
    title = exception?.let { exception::class.simpleName }
))

private fun mapGenericExceptionToResultError(
    exception: Exception,
) = Result.Error(
    NetworkError(
        title = exception::class.simpleName,
        message = exception.message,
    )
)

private fun <T> mapResponse(response: T): Result<T, NetworkError> {
    return if (response !is Response<*>) {
        Result.Success(response)
    } else {
        if (response.isSuccessful) {
            Result.Success(response)
        } else {
            mapHttpExceptionToResultError(
                statusCode = response.code(),
                message = response.message()
            )
        }
    }
}
