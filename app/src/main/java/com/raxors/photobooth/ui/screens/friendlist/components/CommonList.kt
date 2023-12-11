package com.raxors.photobooth.ui.screens.friendlist.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import com.raxors.photobooth.domain.models.User

@Composable
fun CommonList(
    items: LazyPagingItems<User>,
    itemContent: @Composable (User) -> Unit
) {
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(vertical = 16.dp),
        modifier = Modifier.fillMaxHeight()
    ) {
        items(
            count = items.itemCount,
            key = items.itemKey { item -> item.id }
        ) { index ->
            index.let {
                val item = items[it]
                item?.let {
                    itemContent(item)
                }
            }
        }
    }
}