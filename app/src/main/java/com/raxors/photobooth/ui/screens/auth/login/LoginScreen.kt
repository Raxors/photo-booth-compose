package com.raxors.photobooth.ui.screens.auth.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.ClickableText
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.raxors.photobooth.R
import com.raxors.photobooth.core.utils.Extensions.observeAsEvent

@Composable
fun LoginScreen(
    navHostController: NavHostController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    LaunchedEffect(state.navigationRoute) {
        state.navigationRoute?.let { route ->
            viewModel.resetNavigationRoute()
            navHostController.navigate(route)
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(R.string.app_name), style = MaterialTheme.typography.displayLarge)
        Spacer(Modifier.size(32.dp))
        OutlinedTextField(
            value = state.username ?: "",
            onValueChange = {
                viewModel.onEvent(LoginUiEvent.OnUsernameChanged(it))
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
                viewModel.onEvent(LoginUiEvent.OnPasswordChanged(it))
            },
            label = {
                Text(text = stringResource(R.string.password))
            },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.size(16.dp))
        Button(
            onClick = {
                val username = state.username
                val password = state.password
                if (username != null && password != null)
                    viewModel.onEvent(LoginUiEvent.OnLoginClicked(username, password))
            },
            Modifier.width(256.dp)
        ) {
            Text(stringResource(R.string.login))
        }
        Spacer(Modifier.size(16.dp))
        val registrationText = buildAnnotatedString {
            withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.onBackground)) {
                append(stringResource(R.string.don_t_have_an_account) + " ")
            }
            pushStringAnnotation(tag = "registrationTag", annotation = "registrationAnnotation")
            withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                append(stringResource(R.string.sign_up))
            }
        }
        ClickableText(
            text = registrationText
        ) {
            registrationText.getStringAnnotations(it, it).firstOrNull()?.tag?.let { tag ->
                if (tag == "registrationTag")
                    viewModel.onEvent(LoginUiEvent.OnRegisterClicked)
            }
        }
    }
}