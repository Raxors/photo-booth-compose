package com.raxors.photobooth.ui.screens.profile_settings

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowRight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.raxors.photobooth.R
import com.raxors.photobooth.ui.screens.profile.components.EditFieldDialog
import com.raxors.photobooth.ui.screens.profile.components.EditFieldType

@Composable
fun ProfileSettingsScreen(
    navHostController: NavHostController,
    viewModel: ProfileSettingsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val strokeColor = MaterialTheme.colorScheme.secondary

    if (state.isShowEditUsername) {
        EditFieldDialog(
            titleRes = R.string.username,
            defaultText = state.username,
            onConfirm = { username ->
                viewModel.changeUsername(username)
            },
            onCancel = {
                viewModel.onEvent(ProfileSettingsUiEvent.IsEditUsernameShow(false))
            })
    }

    if (state.isShowEditEmail) {
        EditFieldDialog(
            titleRes = R.string.edit_email,
            type = EditFieldType.EMAIL,
            defaultText = state.email,
            onConfirm = { email ->
                viewModel.changeEmail(email)
            },
            onCancel = {
                viewModel.onEvent(ProfileSettingsUiEvent.IsEditEmailShow(false))
            })
    }

    if (state.isShowEditPassword) {
        EditFieldDialog(
            titleRes = R.string.edit_password,
            type = EditFieldType.PASSWORD,
            onConfirm = { password ->
                viewModel.changePassword(password)
            },
            onCancel = {
                viewModel.onEvent(ProfileSettingsUiEvent.IsEditPasswordShow(false))
            })
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(R.string.settings),
                style = MaterialTheme.typography.displayMedium
            )
        }
        Spacer(Modifier.size(32.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .animateContentSize()
                .shadow(8.dp, RectangleShape)
                .drawBehind {
                    drawLine(
                        strokeColor,
                        Offset(0f, size.height),
                        Offset(size.width, size.height),
                        1.dp.toPx()
                    )
                }
                .clickable {
                    viewModel.onEvent(ProfileSettingsUiEvent.IsEditUsernameShow(true))
                }
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface,
                text = stringResource(id = R.string.username)
            )
            Image(
                imageVector = Icons.AutoMirrored.Filled.ArrowRight,
                contentDescription = "Dropdown",
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .animateContentSize()
                .shadow(8.dp, RectangleShape)
                .drawBehind {
                    drawLine(
                        strokeColor,
                        Offset(0f, size.height),
                        Offset(size.width, size.height),
                        1.dp.toPx()
                    )
                }
                .clickable {
                    viewModel.onEvent(ProfileSettingsUiEvent.IsEditEmailShow(true))
                }
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface,
                text = stringResource(id = R.string.email)
            )
            Image(
                imageVector = Icons.AutoMirrored.Filled.ArrowRight,
                contentDescription = "Dropdown",
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .animateContentSize()
                .shadow(8.dp, RectangleShape)
                .drawBehind {
                    drawLine(
                        strokeColor,
                        Offset(0f, size.height),
                        Offset(size.width, size.height),
                        1.dp.toPx()
                    )
                }
                .clickable {
                    viewModel.onEvent(ProfileSettingsUiEvent.IsEditPasswordShow(true))
                }
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface,
                text = stringResource(id = R.string.new_password)
            )
            Image(
                imageVector = Icons.AutoMirrored.Filled.ArrowRight,
                contentDescription = "Dropdown",
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface)
            )
        }
    }
}