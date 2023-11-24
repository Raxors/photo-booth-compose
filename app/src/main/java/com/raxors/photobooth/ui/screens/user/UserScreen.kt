package com.raxors.photobooth.ui.screens.user

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.rounded.PersonAdd
import androidx.compose.material.icons.rounded.PersonRemove
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.raxors.photobooth.BuildConfig
import com.raxors.photobooth.domain.models.Relationship
import com.raxors.photobooth.ui.screens.camera.CameraViewModel

@Composable
fun UserScreen(
    navHostController: NavHostController,
    viewModel: UserViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()

    var isExpandedButtonState by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        state.user?.let { user ->
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
                        when (user.relationship) {
                            Relationship.STRANGER, Relationship.INCOMING_FRIEND_REQUEST -> {
                                FloatingActionButton(onClick = {
                                }) {
                                    Icon(
                                        imageVector = Icons.Rounded.PersonAdd,
                                        contentDescription = "Add user button"
                                    )
                                }
                                if (user.relationship == Relationship.INCOMING_FRIEND_REQUEST || user.relationship == Relationship.OUTGOING_FRIEND_REQUEST) {
                                    Spacer(modifier = Modifier.size(8.dp))
                                    FloatingActionButton(onClick = {
                                    }) {
                                        Icon(
                                            imageVector = Icons.Rounded.PersonRemove,
                                            contentDescription = "Remove user button"
                                        )
                                    }
                                }
                            }
                            Relationship.OUTGOING_FRIEND_REQUEST -> {
                                FloatingActionButton(onClick = {
                                }) {
                                    Icon(
                                        imageVector = Icons.Rounded.PersonAdd,
                                        contentDescription = "Add user button"
                                    )
                                }
                            }
                            else -> {
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
//                                    FloatingActionButton(onClick = {
//                                    }) {
//                                        Icon(
//                                            imageVector = Icons.Rounded.PersonAdd,
//                                            contentDescription = "Add user button"
//                                        )
//                                    }
//                                    Spacer(modifier = Modifier.size(8.dp))
                                        FloatingActionButton(onClick = {
                                        }) {
                                            Icon(
                                                imageVector = Icons.Rounded.PersonRemove,
                                                contentDescription = "Remove user button"
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }


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
                    )
                    Spacer(modifier = Modifier.size(16.dp))
                    Text(
                        style = MaterialTheme.typography.headlineMedium,
                        text = "${user.name}"
                    )
                    Text(
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary,
                        text = "@${user.username}"
                    )
                }
            }
        }
    }
}