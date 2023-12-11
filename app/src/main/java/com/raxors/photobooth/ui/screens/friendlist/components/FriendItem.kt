package com.raxors.photobooth.ui.screens.friendlist.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.raxors.photobooth.BuildConfig
import com.raxors.photobooth.domain.models.User

@Composable
fun FriendItem(
    item: User,
    openUser: (User) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .shadow(8.dp, CircleShape)
            .clickable { openUser(item) }
            .background(MaterialTheme.colorScheme.primaryContainer, CircleShape)
            .padding(8.dp)
    ) {
        AsyncImage(
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer),
            model = BuildConfig.BASE_HOST + item.thumbnailPath,
            contentDescription = item.username
        )
        Spacer(Modifier.size(16.dp))
        Column {
            val name = if (item.name.isNullOrBlank()) item.username.toString() else item.name
            Text(
                name,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Text(
                "@${item.username.toString()}",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}