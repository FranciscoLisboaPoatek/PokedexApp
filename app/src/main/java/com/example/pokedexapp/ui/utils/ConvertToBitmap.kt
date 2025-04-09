package com.example.pokedexapp.ui.utils

import android.content.Context
import android.graphics.Bitmap
import androidx.core.graphics.drawable.toBitmapOrNull
import coil.imageLoader
import coil.request.CachePolicy
import coil.request.ErrorResult
import coil.request.ImageRequest
import coil.request.SuccessResult

suspend fun getImageBitmapFromUrl(
    context: Context,
    url: String?,
): Bitmap? {
    val request =
        ImageRequest.Builder(context).data(url).apply {
            memoryCachePolicy(CachePolicy.DISABLED)
            diskCachePolicy(CachePolicy.DISABLED)
        }.build()

    return when (val result = context.imageLoader.execute(request)) {
        is ErrorResult -> null
        is SuccessResult -> result.drawable.toBitmapOrNull()
    }
}
