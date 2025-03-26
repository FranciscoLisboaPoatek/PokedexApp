package com.example.pokedexapp.domain.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

sealed class Response<out T> {
    class Success<T>(data: T) : Response<T>()
    class Error(ex: Exception) : Response<Nothing>()
}

suspend fun <T> response(dispatcher: CoroutineDispatcher, block: suspend () -> T): Response<T> =
    withContext(dispatcher) {
        return@withContext try {
            Response.Success(block())
        } catch (ex: Exception) {
            Response.Error(ex)
        }
    }
