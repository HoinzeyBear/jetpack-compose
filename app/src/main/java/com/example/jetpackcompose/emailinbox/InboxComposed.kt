package com.example.jetpackcompose.emailinbox

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.CustomAccessibilityAction
import androidx.compose.ui.semantics.customActions
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jetpackcompose.R

@Composable
fun inbox() {
    val viewModel: InboxViewModel = viewModel()
    MaterialTheme {
        EmailInbox(
            modifier = Modifier.fillMaxWidth() ,
            inboxState = viewModel.uiState.collectAsState().value,
            inboxEventListener = viewModel::handleEvent)
    }
    LaunchedEffect(Unit) {
        viewModel.loadContent()
    }
}

@Composable
fun EmailInbox(
    modifier: Modifier = Modifier,
    inboxState: InboxState,
    inboxEventListener: (inboxEvent: InboxEvent) -> Unit) {

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                backgroundColor = MaterialTheme.colors.surface,
                elevation = 0.dp){
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    text = stringResource(id = R.string.title_inbox,
                        inboxState.content!!.count()))
            }
        }) {
        Box(modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center) {
            if(inboxState.status == InboxStatus.LOADING) {
                LoadingIndicator()
            } else if(inboxState.status == InboxStatus.ERROR) {
                DisplayErrorState(modifier = modifier, inboxEventListener)
            } else {
                DisplayEmptyState(inboxEventListener = inboxEventListener)
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun EmailList(
    modifier: Modifier = Modifier,
    emails: List<EmailEntity>) {

    LazyColumn(modifier = modifier) {//todo fillMaxSize ?
        items(emails) { email ->
            SwipeToDismiss(
                modifier = Modifier.semantics {
                    customActions = listOf(
                        CustomAccessibilityAction(deleteEmailLabel) {
                            onEmailDeleted(email.id)
                            true
                        }
                    )
                }.testTag(Tags.TAG_EMAIL + email.id),
                directions = setOf(
                    DismissDirection.StartToEnd
                ),
                dismissThresholds = {
                    FractionalThreshold(0.15f)
                },
                background = {
                    EmailItemBackground(
                        modifier = Modifier
                            .height(emailHeightAnimation)
                            .fillMaxWidth(),
                        targetValue = dismissState.targetValue,
                        currentValue = dismissState.currentValue
                    )
                },
                state = dismissState,
                dismissContent = {
                    EmailItem(
                        modifier = Modifier
                            .height(emailHeightAnimation)
                            .fillMaxWidth(),
                        email = email,
                        dismissDirection = dismissState.dismissDirection
                    )
                }
            )
        }
    }
}

@Composable
fun EmailItem(
    modifier: Modifier = Modifier,
    email: EmailEntity
) {
    Card(
        modifier = modifier.padding(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp).fillMaxWidth()) {
            Text(
                text = email.title,
                fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = email.description,
                fontSize = 14.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis)
        }
    }
}

@Composable
fun EmailItemBackground(
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.padding(horizontal = 20.dp)) {
        Icon(modifier = Modifier.align(Alignment.CenterStart) ,imageVector = Icons.Default.Delete, contentDescription = null)
    }
}

@Composable
fun LoadingIndicator() {
    CircularProgressIndicator()
}

@Composable
fun DisplayErrorState(
    modifier: Modifier = Modifier,
    inboxEventListener: (inboxEvent: InboxEvent) -> Unit
) {
    Column(modifier = modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {

        Text(text = stringResource(id = R.string.message_content_error))

        Spacer(modifier = Modifier.height(12.dp))

        Button(onClick = {
            inboxEventListener(InboxEvent.RefreshContent)
        }) {
            Text(text = stringResource(id = R.string.label_try_again))
        }
    }
}

@Composable
fun DisplayEmptyState(
    modifier: Modifier = Modifier,
    inboxEventListener: (inboxEvent: InboxEvent) -> Unit
) {
    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(id = R.string.message_empty_content))

        Spacer(modifier = Modifier.height(12.dp))

        Button(onClick = {
            inboxEventListener(InboxEvent.RefreshContent)
        }) {
            Text(text = stringResource(id = R.string.label_check_again))
        }
    }
}
