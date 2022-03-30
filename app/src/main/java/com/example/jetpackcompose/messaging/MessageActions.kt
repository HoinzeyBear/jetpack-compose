package com.example.jetpackcompose.messaging

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.jetpackcompose.R

@Composable
fun MessageActions(modifier: Modifier = Modifier,
                    onDismiss: () -> Unit,
                    onUnsendMessage: () -> Unit) {

    var showDialog by remember{ mutableStateOf(true) }
    if(showDialog) {
        Dialog(onDismissRequest = onDismiss) {
            Box(modifier = modifier.background(MaterialTheme.colors.surface)
                .sizeIn(minWidth = 280.dp, minHeight = 80.dp),
                contentAlignment = Alignment.Center) {
                TextButton(onClick = {
                    onUnsendMessage()
                    showDialog = false
                }) {
                    Text(text = stringResource(
                        id = R.string.action_unsend_message))
                }
            }
        }
    }
}