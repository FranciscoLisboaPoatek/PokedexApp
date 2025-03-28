package com.example.pokedexapp.domain.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

sealed class Response<out T> {
    class Success<T>(val data: T) : Response<T>()

    class Error(val ex: Exception) : Response<Nothing>()
}

suspend fun <T> response(
    dispatcher: CoroutineDispatcher,
    block: suspend () -> T,
): Response<T> =
    withContext(dispatcher) {
        return@withContext try {
            Response.Success(block())
        } catch (ex: Exception) {
            Response.Error(ex)
        }
    }

fun <T> response(block: () -> T): Response<T> {
    return try {
        Response.Success(block())
    } catch (ex: Exception) {
        Response.Error(ex)
    }
}
