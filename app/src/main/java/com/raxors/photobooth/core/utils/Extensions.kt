package com.raxors.photobooth.core.utils

import android.content.Context
import android.graphics.Bitmap
import android.util.Base64
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object Extensions {

    @Composable
    fun <T> Flow<T>.observeAsEvent(onEvent: (T) -> Unit) {
        val lifecycleOwner = LocalLifecycleOwner.current
        LaunchedEffect(this, lifecycleOwner.lifecycle) {
            withContext(Dispatchers.Main.immediate) {
                collect(onEvent)
            }
        }
    }

    fun Bitmap.encodeImage(): String {
        val byteArray = ByteArrayOutputStream()
        compress(Bitmap.CompressFormat.JPEG, 25, byteArray)
        val b = byteArray.toByteArray()
        return Base64.encodeToString(b, Base64.NO_WRAP)
    }

    fun String.toDateString(): String {
        val pattern = DateTimeFormatter.ofPattern("dd-MM-yyy HH:mm:ss.SSS z")
        val localDate = LocalDateTime.parse(this, pattern)
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyy")
        return localDate.format(formatter)
    }

    fun String.toTimeString(): String {
        val pattern = DateTimeFormatter.ofPattern("dd-MM-yyy HH:mm:ss.SSS z")
        val localDate = LocalDateTime.parse(this, pattern)
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        return localDate.format(formatter)
    }

}