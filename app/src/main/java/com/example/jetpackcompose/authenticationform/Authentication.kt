package com.example.jetpackcompose.authenticationform

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jetpackcompose.R

@Composable
fun Authentication() {
    val viewModel: AuthenticationViewModel = viewModel()
    MaterialTheme {
        AuthenticationContent(
            modifier = Modifier.fillMaxWidth(),
            authenticationState =
            viewModel.uiState.collectAsState().value,
            handleEvent = viewModel::handleEvent)
    }
}

@Composable
fun AuthenticationContent(
    modifier: Modifier = Modifier,
    authenticationState: AuthenticationState,
    handleEvent: (event: AuthenticationViewModel.AuthenticationEvent) -> Unit) {

    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        if(authenticationState.isLoading) {
            CircularProgressIndicator()
        } else {
            AuthenticationForm(
                modifier = Modifier.fillMaxWidth() ,
                authenticationState = authenticationState,
                onEmailChanged = { email ->
                    handleEvent(
                        AuthenticationViewModel.AuthenticationEvent.EmailChanged(email))
                })

        }
    }
}

@Composable
fun AuthenticationForm(modifier: Modifier = Modifier, authenticationState: AuthenticationState,
                       onEmailChanged: (email: String) -> Unit) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.height(32.dp))
        AuthenticationTitle(authenticationMode = authenticationState.authenticationMode)
        Spacer(modifier = Modifier.height(32.dp))
        Card(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp), elevation = 4.dp) {
            Column(modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally) {

                EmailInput(
                    modifier = Modifier.fillMaxWidth(),
                    email = authenticationState.email ?: "",
                    onEmailChanged = onEmailChanged
                )
            }
        }
    }
}

@Composable
fun AuthenticationTitle(
    modifier: Modifier = Modifier,
    authenticationMode: AuthenticationMode) {

    Text(text = stringResource(
            if (authenticationMode == AuthenticationMode.SIGN_IN) {
                R.string.label_sign_in_to_account
            } else {
                R.string.label_sign_up_for_account
            }
        ), fontSize = 24.sp, fontWeight = FontWeight.Black
    )
}

@Composable
fun EmailInput(
    modifier: Modifier = Modifier,
    email: String?,
    onEmailChanged: (email: String) -> Unit
) {
    TextField(modifier = modifier,
        value = email ?: "",
        onValueChange = { email ->
            onEmailChanged(email)
        }, label = {
            Text(text = stringResource(id = R.string.label_email))
        },
        singleLine = true,
        leadingIcon = {
            Icon(imageVector = Icons.Default.Email, contentDescription = null)
        }
    )
}

//279