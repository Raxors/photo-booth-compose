package com.raxors.photobooth.ui.screens.profile.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.raxors.photobooth.R

@Composable
fun EditFieldDialog(
    @StringRes
    titleRes: Int,
    type: EditFieldType = EditFieldType.TEXT,
    defaultText: String = "",
    onConfirm: (text: String) -> Unit,
    onCancel: () -> Unit
) {
    var text by remember { mutableStateOf(defaultText) }
    Dialog(
        onDismissRequest = { onCancel() }
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Spacer(modifier = Modifier.size(16.dp))
            Text(
                text = stringResource(id = titleRes),
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.Center),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.size(8.dp))
            OutlinedTextField(
                modifier = Modifier.padding(horizontal = 16.dp),
                value = text,
                onValueChange = { text = it },
                keyboardOptions = KeyboardOptions(
                    keyboardType = when (type) {
                        EditFieldType.PASSWORD -> KeyboardType.Password
                        EditFieldType.EMAIL -> KeyboardType.Email
                        else -> KeyboardType.Text
                    }
                ),
                visualTransformation = if (type == EditFieldType.PASSWORD) PasswordVisualTransformation() else VisualTransformation.None
            )
            Spacer(modifier = Modifier.size(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(onClick = { onConfirm(text) }) {
                    Text(text = stringResource(id = R.string.confirm))
                }
                Button(onClick = { onCancel() }) {
                    Text(text = stringResource(id = R.string.cancel))
                }
            }
            Spacer(modifier = Modifier.size(16.dp))
        }
    }
}

enum class EditFieldType {
    TEXT, PASSWORD, EMAIL
}