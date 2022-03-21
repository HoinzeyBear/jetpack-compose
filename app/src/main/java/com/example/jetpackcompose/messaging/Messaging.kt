package com.example.jetpackcompose.messaging

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jetpackcompose.R
import com.example.jetpackcompose.messaging.model.ConversationState
import com.example.jetpackcompose.messaging.model.Message
import com.example.jetpackcompose.messaging.model.MessageDirection
import java.text.SimpleDateFormat

@Composable
fun Messaging() {
    val viewModel: ConversationViewModel = viewModel()
    MaterialTheme {
        Conversation(modifier = Modifier.fillMaxSize(),
            state = viewModel.uiState.collectAsState().value)
    }
}

@Composable
fun Conversation(modifier: Modifier = Modifier, state: ConversationState) {
    Column(modifier = modifier) {
        Header(Modifier.fillMaxWidth(), onClose = {})

        Messages(
            modifier = Modifier.weight(1f)
        )
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

@Composable
fun Messages(
    modifier: Modifier = Modifier,
    messageList: List<Message>? = null) {

    if(messageList.isNullOrEmpty()) {
        EmptyConversation()
    } else {
        LazyColumn(
            modifier = modifier,
            state = rememberLazyListState(),
            contentPadding = PaddingValues(top = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)) {

            items(messageList) { next ->
                Message(modifier = modifier.fillMaxWidth(), message = next)
            }
        }
    }
}

@Composable
fun EmptyConversation(
    modifier: Modifier = Modifier) {

    Box(modifier = modifier) {
        Text(text = stringResource(
            id = R.string.label_no_messages))
    }
}

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

    val dateFormat = remember { SimpleDateFormat("hh:mm") }
    Column(modifier = parentModifier){
        Box(modifier = Modifier
            .align(alignment)
            .background(Color.LightGray, RoundedCornerShape(6.dp))
            .padding(8.dp)) {

            MessageBody(modifier = Modifier.align(Alignment.Center),
                message = message)
        }
        Text(
            modifier = Modifier.padding(top = 4.dp),
            text = dateFormat.format(message.dateTime.time),
            fontSize = 12.sp
        )
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