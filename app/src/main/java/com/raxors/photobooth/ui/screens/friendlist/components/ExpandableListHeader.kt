package com.raxors.photobooth.ui.screens.friendlist.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp

@Composable
fun ExpandableListHeader(
    title: String,
    isExpanded: Boolean,
    clickExpand: (Boolean) -> Unit,
    content: @Composable (Boolean) -> Unit
) {
    val cornerRadius = animateDpAsState(targetValue = if (isExpanded) 16.dp else 0.dp, label = "")
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .animateContentSize()
            .background(MaterialTheme.colorScheme.surfaceColorAtElevation(5.dp), RoundedCornerShape(bottomStart = cornerRadius.value, bottomEnd = cornerRadius.value))
            .clip(RoundedCornerShape(bottomStart = cornerRadius.value, bottomEnd = cornerRadius.value))
            .clickable { clickExpand(isExpanded) }
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onSurface,
            text = title
        )
        Image(
            imageVector = if (isExpanded) Icons.Default.ArrowDropDown else Icons.Default.ArrowDropUp,
            contentDescription = "Dropdown",
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface)
        )
    }
    content(isExpanded)
}