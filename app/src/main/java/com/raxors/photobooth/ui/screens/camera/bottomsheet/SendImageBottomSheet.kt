package com.raxors.photobooth.ui.screens.camera.bottomsheet

import android.graphics.Bitmap
import android.util.Base64
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.raxors.photobooth.R
import com.raxors.photobooth.ui.screens.camera.components.AllItemSmall
import com.raxors.photobooth.ui.screens.camera.components.UserItemSmall
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SendImageBottomSheet(
    onDismiss: () -> Unit,
    bitmap: ImageBitmap?,
    viewModel: SendImageViewModel = hiltViewModel()
) {
    val modalBottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val state by viewModel.state.collectAsState()
    val friends = viewModel.getFriendListStateFlow().collectAsLazyPagingItems()
    if (state.isPhotoSent) {
        LaunchedEffect(state) {
            modalBottomSheetState.hide()
            viewModel.onEvent(SendImageUiEvent.CloseBottomSheet)
            onDismiss()
        }
    }
    ModalBottomSheet(
        onDismissRequest = {
            viewModel.onEvent(SendImageUiEvent.CloseBottomSheet)
            onDismiss()
        },
        sheetState = modalBottomSheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            bitmap?.let {
                Image(
                    modifier = Modifier
                        .size(256.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    bitmap = bitmap,
                    contentDescription = "Image"
                )
            }
            Spacer(Modifier.size(32.dp))
            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(
                    start = 16.dp,
                    end = 16.dp,
                    top = 16.dp,
                    bottom = 32.dp
                ),
            ) {
                item {
                    AllItemSmall(
                        onCLick = {
                            viewModel.onEvent(SendImageUiEvent.CheckAll)
                        },
                        isChecked = state.isAllChecked
                    )
                }
                items(
                    count = friends.itemCount,
                    key = friends.itemKey { item -> item.id }
                ) { index ->
                    index.let {
                        val item = friends[it]
                        item?.let { user ->
                            UserItemSmall(
                                user = item,
                                isChecked = state.selectedFriends.contains(user),
                                onCLick = {
                                    viewModel.onEvent(SendImageUiEvent.CheckUser(user))
                                }
                            )
                        }
                    }
                }
            }
            Button(
                onClick = {
                    bitmap?.asAndroidBitmap()?.let {
                        viewModel.onEvent(SendImageUiEvent.SendPhoto(it))
                    }
                },
                Modifier.width(256.dp)
            ) {
                Text(stringResource(R.string.send_photo))
            }
            Spacer(Modifier.size(128.dp))
        }
    }
}