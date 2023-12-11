package com.raxors.photobooth.ui.screens.camera.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.raxors.photobooth.BuildConfig
import com.raxors.photobooth.domain.models.User

@Composable
fun UserItemSmall(
    user: User,
    isChecked: Boolean,
    onCLick: () -> Unit
) {
    Column(
        modifier = Modifier
            .clip(CircleShape)
            .clickable { onCLick() },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            modifier = Modifier
                .size(36.dp)
                .border(
                    border = BorderStroke(
                        width = if (isChecked) 2.dp else 0.dp,
                        color = MaterialTheme.colorScheme.primary),
                    shape = CircleShape
                )
                .clip(CircleShape),
            model = BuildConfig.BASE_HOST + user.thumbnailPath,
            contentDescription = user.id,
            contentScale = ContentScale.FillBounds
        )
        Text(
            text = user.username ?: "",
            style = MaterialTheme.typography.titleSmall
        )
    }
}