package com.raxors.photobooth.ui.screens.profile

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Logout
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.raxors.photobooth.BuildConfig
import com.raxors.photobooth.R
import com.raxors.photobooth.core.navigation.CommonScreen
import com.raxors.photobooth.ui.screens.profile.components.EditFieldDialog

@Composable
fun ProfileScreen(
    navHostController: NavHostController,
    logout: () -> Unit,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    var isExpandedButtonState by remember {
        mutableStateOf(false)
    }

    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia()) { uri ->
        uri?.let {
            viewModel.changeAvatar(getBitmapFromUri(uri, context))
        }
    }

    if (state.isShowEditName) {
        EditFieldDialog(
            titleRes = R.string.edit_name,
            onConfirm = { name ->
                viewModel.changeName(name)
            },
            onCancel = {
                viewModel.onEvent(ProfileUiEvent.IsEditNameShow(false))
            })
    }
    Box(modifier = Modifier.padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Column(
                modifier = Modifier.background(
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    shape = RoundedCornerShape(16.dp)
                )
            ) {
                FloatingActionButton(
                    containerColor = if (isExpandedButtonState) MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.primaryContainer,
                    onClick = {
                        isExpandedButtonState = !isExpandedButtonState
                    }) {
                    Icon(
                        imageVector = Icons.Rounded.Menu,
                        contentDescription = "Menu button"
                    )
                }
                AnimatedVisibility(
                    visible = isExpandedButtonState,
                    enter = expandVertically() + fadeIn(),
                    exit = shrinkVertically() + fadeOut()
                ) {
                    Column {
                        Spacer(modifier = Modifier.size(8.dp))
                        FloatingActionButton(onClick = {
                            navHostController.navigate(CommonScreen.ProfileSettings.route)
                        }) {
                            Icon(
                                imageVector = Icons.Rounded.Settings,
                                contentDescription = "Settings button"
                            )
                        }
                        Spacer(modifier = Modifier.size(8.dp))
                        FloatingActionButton(onClick = {
                            logout()
                        }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Rounded.Logout,
                                contentDescription = "Logout Button"
                            )
                        }
                    }
                }
            }
        }
        state.profile?.let { user ->
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = BuildConfig.BASE_HOST + user.imagePath,
                    contentDescription = "User avatar",
                    modifier = Modifier
                        .size(256.dp)
                        .clip(CircleShape)
                        .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
                        .clickable {
                            launcher.launch(PickVisualMediaRequest(
                                mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
                            ))
                        }
                )
                Spacer(modifier = Modifier.size(16.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.size(42.dp))
                    Text(
                        style = MaterialTheme.typography.headlineMedium,
                        text = "${user.name}"
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Image(
                        modifier = Modifier
                            .border(1.dp, MaterialTheme.colorScheme.primary, CircleShape)
                            .padding(4.dp)
                            .clickable {
                                viewModel.onEvent(ProfileUiEvent.IsEditNameShow(true))
                            }
                            .background(Color.Transparent, CircleShape),
                        imageVector = Icons.Rounded.Edit,
                        contentDescription = "Edit Name",
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
                    )
                }
                Text(
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary,
                    text = "@${user.username}"
                )
            }
        }
    }
}

private fun getBitmapFromUri(uri: Uri, context: Context): Bitmap {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        val source = ImageDecoder.createSource(context.contentResolver, uri)
        ImageDecoder.decodeBitmap(source)
    } else {
        MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
    }
}