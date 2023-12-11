package com.raxors.photobooth.ui.screens.camera.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.raxors.photobooth.R

@Composable
fun AllItemSmall(
    isChecked: Boolean,
    onCLick: () -> Unit
) {
    Box(
        modifier = Modifier.padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        val color = if (isChecked) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
        Text(
            modifier = Modifier
                .aspectRatio(1f)
                .border(BorderStroke(2.dp, color), CircleShape)
                .clip(CircleShape)
                .clickable { onCLick() }
                .wrapContentSize(),
            text = stringResource(R.string.all),
            textAlign = TextAlign.Center,
            color = color,
            style = MaterialTheme.typography.titleLarge
        )
    }
}