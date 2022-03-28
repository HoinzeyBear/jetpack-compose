package com.example.jetpackcompose.messaging

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jetpackcompose.R
import com.example.jetpackcompose.messaging.model.ConversationState
import com.example.jetpackcompose.messaging.model.Message
import com.example.jetpackcompose.messaging.model.MessageDirection
import java.text.SimpleDateFormat
import java.util.*

@ExperimentalFoundationApi
@ExperimentalAnimationApi
@Composable
fun Messaging() {
    val viewModel: ConversationViewModel = viewModel()
    MaterialTheme {
        Conversation(modifier = Modifier.fillMaxSize(),
            state = viewModel.uiState.collectAsState().value,
            viewModel::handleEvent)
    }
}

@ExperimentalFoundationApi
@ExperimentalAnimationApi
@Composable
fun Conversation(modifier: Modifier = Modifier,
                 state: ConversationState,
                handleEvent: (event: ConversationEvent) -> Unit) {
    Column(modifier = modifier) {
        Header(Modifier.fillMaxWidth(), onClose = {})

        Messages(
            modifier = Modifier.weight(1f),
            messageList = state.messages
        )

        InputBar(sendMessage = { message ->
            handleEvent(ConversationEvent.SendMessage(message))
        },
        contacts = state.contacts)
    }
}

@Composable
fun Header(modifier: Modifier = Modifier, onClose: () -> Unit) {
    TopAppBar(modifier = modifier) {
        IconButton(onClick = { onClose() }) {
            Icon(imageVector = Icons.Default.Close, contentDescription = stringResource(id = R.string.cd_close_conversation))
        }
        Text(text = stringResource(id = R.string.title_chat), fontSize = 18.sp)
    }
}

@ExperimentalFoundationApi
@ExperimentalAnimationApi
@Composable
fun Messages(
    modifier: Modifier = Modifier,
    messageList: List<Message>? = null) {

    if(messageList.isNullOrEmpty()) {
        EmptyConversation()
    } else {
        val grouped = groupMessagesByDate(messageList)
        LazyColumn(
            modifier = modifier,
            state = rememberLazyListState(),
            contentPadding = PaddingValues(top = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)) {

            grouped.onEachIndexed { index, entry ->
                stickyHeader {
                    MessageHeader(modifier = Modifier.fillMaxWidth(),
                        isToday = isToday(entry.key),
                        date = entry.key)
                }

                items(entry.value) { message ->
                    Message(
                        modifier = Modifier.fillMaxWidth(),
                        message = message)
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
//            items(messageList) { next ->
//                Message(modifier = modifier.fillMaxWidth(), message = next)
//            }
        }
    }
}

fun groupMessagesByDate(
    messages: List<Message>): Map<Calendar, List<Message>> {
    return messages.groupBy {
        it.dateTime.apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
    }
}

fun isToday(
    calendar: Calendar): Boolean {
    val today = Calendar.getInstance()
    return calendar.get(Calendar.YEAR) == today.get(Calendar.YEAR) &&
            calendar.get(Calendar.MONTH) == today.get(Calendar.MONTH) &&
            calendar.get(Calendar.DAY_OF_MONTH) == today.get(Calendar.DAY_OF_MONTH)
}

@SuppressLint("SimpleDateFormat")
@Composable
fun MessageHeader(
    modifier: Modifier = Modifier,
    isToday: Boolean,
    date: Calendar
) {
    val label = if (isToday) {
        stringResource(id = R.string.label_today)
    } else {
        val dateFormat = SimpleDateFormat("dd MMM yyyy")
        dateFormat.format(date.time)
    }
    Text(
        modifier = modifier
            .background(
                MaterialTheme.colors.onSurface.copy(
                    alpha = 0.05f
                )
            )
            .padding(vertical = 4.dp),
        text = label,
        fontSize = 14.sp,
        textAlign = TextAlign.Center
    )
}

@Composable
fun EmptyConversation(
    modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        Text(text = stringResource(
            id = R.string.label_no_messages))
    }
}

@ExperimentalAnimationApi
@SuppressLint("SimpleDateFormat")
@Composable
fun Message(
    modifier: Modifier = Modifier,
    message: Message) {

    val parentModifier = if (message.direction == MessageDirection.SENT) {
        modifier.padding(end = 16.dp)
    } else modifier.padding(start = 16.dp)

    val alignment = if (message.direction == MessageDirection.SENT) {
        Alignment.End
    } else Alignment.Start

    var displaySentTime by remember { mutableStateOf(false) }
    val dateFormat = remember { SimpleDateFormat("hh:mm") }
    Column(modifier = parentModifier.pointerInput(Unit) {
        detectTapGestures(onTap = {
            displaySentTime = !displaySentTime
        })
    }){
        Box(modifier = Modifier
            .align(alignment)
            .background(Color.LightGray, RoundedCornerShape(6.dp))
            .padding(8.dp)) {

            MessageBody(modifier = Modifier.align(Alignment.Center),
                message = message)
        }
        AnimatedVisibility(modifier = Modifier.align(alignment),
            visible = displaySentTime) {
            Text(
                modifier = Modifier
                    .padding(top = 4.dp),
                text = dateFormat.format(message.dateTime.time),
                fontSize = 12.sp)
        }
    }
}

@Composable
fun MessageBody(
    modifier: Modifier = Modifier,
    message: Message) {

    if (message.message != null) {
        Text(
            modifier = modifier,
            text = message.message
        )
    } else if (message.image != null) {
        Image(modifier = modifier.size(120.dp),
            bitmap = BitmapFactory.decodeResource(LocalContext.current.resources, message.image).asImageBitmap(),
        contentDescription = message.altText)
    }
}

//@Preview(name = "Message preview")
//@Composable
//fun preview_message() {
//    val message = Message(id = "25", direction = MessageDirection.SENT, Calendar.getInstance(), "Me", "Coming out?")
//    Message(message = message)
//}