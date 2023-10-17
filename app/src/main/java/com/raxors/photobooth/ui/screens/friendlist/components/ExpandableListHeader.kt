package com.raxors.photobooth.ui.screens.friendlist.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ExpandableListHeader(
    title: String,
    itemCount: Int,
    isExpanded: Boolean,
    clickExpand: (Boolean) -> Unit,
    content: @Composable (Boolean) -> Unit
) {

    Box(modifier = Modifier.clickable { clickExpand(isExpanded) }.padding(horizontal = 16.dp)) {
        Text(style = MaterialTheme.typography.titleLarge, text = "$title ($itemCount)")
    }
    content(isExpanded)
}