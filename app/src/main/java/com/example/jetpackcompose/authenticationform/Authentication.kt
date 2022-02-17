package com.example.jetpackcompose.authenticationform

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
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
                onEmailChanged = {
                    handleEvent(
                        AuthenticationViewModel.AuthenticationEvent.EmailChanged(it))
                },
            onPasswordChanged = {
                handleEvent(
                    AuthenticationViewModel.AuthenticationEvent.PasswordChanged(it))
            },
            onDoneClicked = {
                handleEvent(AuthenticationViewModel.AuthenticationEvent.Authenticate)
            })

        }
    }
}

@Composable
fun AuthenticationForm(modifier: Modifier = Modifier, authenticationState: AuthenticationState,
                       onEmailChanged: (email: String) -> Unit,
                       onPasswordChanged: (password: String) -> Unit,
                        onDoneClicked: () -> Unit) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        val passwordFocusRequester = FocusRequester()
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
                    onEmailChanged = onEmailChanged,
                    onNextClicked = {passwordFocusRequester.requestFocus()})
                Spacer(modifier = Modifier.height(16.dp))
                PasswordInput(
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(passwordFocusRequester),
                    password = authenticationState.password ?: "",
                    onPasswordChanged = onPasswordChanged,
                    onDoneClicked = onDoneClicked)
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
    onEmailChanged: (email: String) -> Unit,
    onNextClicked: () -> Unit
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
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(
            onNext = {onNextClicked()}
        )
    )
}

@Composable
fun PasswordInput(
    modifier: Modifier = Modifier,
    password: String,
    onPasswordChanged: (email: String) -> Unit,
    onDoneClicked: () -> Unit
) {
    var isPasswordHidden by remember {
        mutableStateOf(true)
    }

    TextField(
        modifier = modifier,
        value = password,
        onValueChange = {
            onPasswordChanged(it)
        },
        singleLine = true,
        label = {
            Text(text = stringResource(
                    id = R.string.label_password))
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = null)
        },
        trailingIcon = {
            Icon(
                modifier = Modifier.clickable(onClickLabel = if (isPasswordHidden) {
                        stringResource(id = R.string.cd_show_password)
                    } else stringResource(id = R.string.cd_hide_password)) {
                    isPasswordHidden = !isPasswordHidden
                },
                imageVector = if (isPasswordHidden) {
                    Icons.Default.Phone
                } else Icons.Default.Person,//missing Visibility & VisibilityOff options
                contentDescription = null)
        }, visualTransformation = if (isPasswordHidden) {
            PasswordVisualTransformation()
        } else VisualTransformation.None,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done,
        keyboardType = KeyboardType.Password),
        keyboardActions = KeyboardActions(onDone = {onDoneClicked()})
    )
}