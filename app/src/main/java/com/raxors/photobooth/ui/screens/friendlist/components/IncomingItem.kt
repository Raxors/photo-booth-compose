package com.raxors.photobooth.ui.screens.friendlist.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.raxors.photobooth.BuildConfig
import com.raxors.photobooth.R
import com.raxors.photobooth.domain.models.User

@Composable
fun IncomingItem(
    item: User,
    accept: (User) -> Unit,
    decline: (User) -> Unit,
    openUser: (User) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { openUser(item) }
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
                text = name,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "@${item.username.toString()}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(Modifier.size(8.dp))
            Row {
                Button(
                    modifier = Modifier.height(32.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    onClick = { accept(item) },
                ) {
                    Text(stringResource(R.string.add_friend))
                }
                Spacer(Modifier.size(8.dp))
                OutlinedButton(
                    modifier = Modifier.height(32.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    onClick = { decline(item) }
                ) {
                    Text(stringResource(R.string.decline), style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}