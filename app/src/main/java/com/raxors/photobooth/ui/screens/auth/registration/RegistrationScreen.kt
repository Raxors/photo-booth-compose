package com.raxors.photobooth.ui.screens.auth.registration

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.raxors.photobooth.R

@Composable
fun RegistrationScreen(
    navHostController: NavHostController,
    viewModel: RegistrationViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    LaunchedEffect(state.navigationRoute) {
        state.navigationRoute?.let { route ->
            navHostController.navigate(route)
            viewModel.resetNavigationRoute()
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.registration),
            style = MaterialTheme.typography.displayLarge
        )
        Spacer(Modifier.size(32.dp))
        OutlinedTextField(
            value = state.username ?: "",
            onValueChange = {
                viewModel.onEvent(RegistrationUiEvent.OnUsernameChanged(it))
            },
            label = {
                Text(text = stringResource(R.string.username))
            },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.size(16.dp))
        OutlinedTextField(
            value = state.password ?: "",
            onValueChange = {
                viewModel.onEvent(RegistrationUiEvent.OnPasswordChanged(it))
            },
            label = {
                Text(text = stringResource(R.string.password))
            },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.size(16.dp))
        OutlinedTextField(
            value = state.email ?: "",
            onValueChange = {
                viewModel.onEvent(RegistrationUiEvent.OnEmailChanged(it))
            },
            label = {
                Text(text = stringResource(R.string.email))
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.size(16.dp))
        Button(
            onClick = {
                val username = state.username
                val password = state.password
                val email = state.email
                if (username != null && password != null && email != null)
                    viewModel.onEvent(
                        RegistrationUiEvent.OnRegistrationClicked(
                            username,
                            password,
                            email
                        )
                    )
            },
            Modifier.width(256.dp)
        ) {
            Text(stringResource(R.string.registration))
        }
    }
}