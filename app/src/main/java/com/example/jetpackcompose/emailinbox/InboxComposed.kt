package com.example.jetpackcompose.emailinbox

import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
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

@ExperimentalMaterialApi
@Composable
fun Inbox() {
    val viewModel: InboxViewModel = viewModel()
    MaterialTheme {
        EmailInbox(
            modifier = Modifier.fillMaxWidth(),
            inboxState = viewModel.uiState.collectAsState().value,
            inboxEventListener = viewModel::handleEvent
        )
    }
    LaunchedEffect(Unit) {
        viewModel.loadContent()
    }
}

@ExperimentalMaterialApi
@Composable
fun EmailInbox(
    modifier: Modifier = Modifier,
    inboxState: InboxState,
    inboxEventListener: (inboxEvent: InboxEvent) -> Unit
) {

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                backgroundColor = MaterialTheme.colors.surface,
                elevation = 0.dp
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    text = stringResource(
                        id = R.string.title_inbox,
                        inboxState.content?.count() ?: "bug"
                    )
                )
            }
        }) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            if (inboxState.status == InboxStatus.LOADING) {
                LoadingIndicator()
            } else if (inboxState.status == InboxStatus.ERROR) {
                DisplayErrorState(modifier = modifier, inboxEventListener)
            } else if (inboxState.status == InboxStatus.HAS_EMAILS) {
                EmailList(
                    modifier = Modifier.fillMaxSize(),
                    emails = inboxState.content!!,
                    inboxEventListener = inboxEventListener
                )
            } else {
                DisplayEmptyState(inboxEventListener = inboxEventListener)
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun EmailList(
    modifier: Modifier = Modifier,
    emails: List<EmailEntity>,
    inboxEventListener: (inboxEvent: InboxEvent) -> Unit
) {

    val deleteEmailLabel = stringResource(id = R.string.cd_delete_email)
    //todo the swipe is removing the email but not all 5 are being redrawn. Step through the testing chapters to see what is up!
    //todo i was missing the bloody key!!
    LazyColumn(modifier = modifier) {
        items(emails, key = { item -> item.id }) { email ->
            var isEmailItemDismissed by remember { mutableStateOf(false) }
            val dismissState = rememberDismissState(
                confirmStateChange = {
                if (it == DismissValue.DismissedToEnd) {
                    isEmailItemDismissed = true
                }
                true
            })
            val emailHeightAnimation by animateDpAsState(
                targetValue = if (isEmailItemDismissed.not()) 120.dp
                else 0.dp,
                animationSpec = tween(delayMillis = 300),
                finishedListener = {
                    inboxEventListener(InboxEvent.DeleteEmail(email.id))
                })
            SwipeToDismiss(
                modifier = Modifier.semantics {
                    customActions = listOf(
                        CustomAccessibilityAction(deleteEmailLabel) {
                            inboxEventListener(InboxEvent.DeleteEmail(email.id))
                            true
                        })
                }
//                    .testTag(Tags.TAG_EMAIL + email.id)
                ,
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
                        dismissState = dismissState,
//                        targetValue = dismissState.targetValue,
//                        currentValue = dismissState.currentValue
                    )
                },
                state = dismissState,
                dismissContent = {
                    EmailItem(
                        modifier = Modifier
                            .height(emailHeightAnimation)
                            .fillMaxWidth(),
                        email = email,
                        dismissState = dismissState,
//                        dismissDirection = dismissState.dismissDirection
                    )
                })

            val dividerVisibilityAnimation by animateFloatAsState(
                targetValue = if (dismissState.targetValue ==
                    DismissValue.Default
                ) {
                    1f
                } else 0f,
                animationSpec = tween(delayMillis = 300)
            )

            Divider(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .alpha(dividerVisibilityAnimation)
            )

        }
    }
}

@ExperimentalMaterialApi
@Composable
fun EmailItem(
    modifier: Modifier = Modifier,
    email: EmailEntity,
    dismissState: DismissState
) {

    val cardElevation = animateDpAsState(
        if (dismissState.dismissDirection != null) {
            4.dp
        } else 0.dp
    ).value

    Card(
        modifier = modifier.padding(16.dp), elevation = cardElevation
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = email.title,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = email.description,
                fontSize = 14.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun EmailItemBackground(
    modifier: Modifier = Modifier,
    dismissState: DismissState
) {

    val backgroundColor by animateColorAsState(
        targetValue = when (dismissState.targetValue) {
            DismissValue.DismissedToEnd ->
                MaterialTheme.colors.error
            else -> MaterialTheme.colors.background
        },
        animationSpec = tween()
    )

    val iconColor by animateColorAsState(
        targetValue = when (dismissState.targetValue) {
            DismissValue.DismissedToEnd ->
                MaterialTheme.colors.onError
            else -> MaterialTheme.colors.onSurface
        },
        animationSpec = tween()
    )

    val scale by animateFloatAsState(
        targetValue = if (dismissState.targetValue ==
            DismissValue.DismissedToEnd
        ) {
            1f
        } else 0.75f
    )

    Box(
        modifier = modifier
            .padding(horizontal = 20.dp)
            .background(backgroundColor)
    ) {
        if (dismissState.currentValue == DismissValue.Default) {
            Icon(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .scale(scale),
                tint = iconColor, imageVector = Icons.Default.Delete, contentDescription = null
            )
        }
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
