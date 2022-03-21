package com.example.jetpackcompose.messaging

import androidx.lifecycle.ViewModel
import com.example.jetpackcompose.messaging.model.ConversationState
import com.example.jetpackcompose.messaging.model.Message
import com.example.jetpackcompose.messaging.model.MessageDirection
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.*

class ConversationViewModel: ViewModel() {

    val uiState = MutableStateFlow(ConversationState())

    fun handleEvent(conversationEvent: ConversationEvent) {
        when (conversationEvent) {
            is ConversationEvent.SendMessage -> {
                uiState.value = uiState.value.copy(
                    messages = uiState.value.messages
                        .toMutableList().apply {
                            add(buildMessage(
                                conversationEvent.message))
                        }.toList()
                )
            }
            is ConversationEvent.UnsendMessage -> {
                uiState.value = uiState.value.copy(
                    messages = messagesExcluding(
                        conversationEvent.id)
                )
            }
            is ConversationEvent.SelectMessage -> {
                uiState.value = uiState.value.copy(
                    selectedMessage = uiState.value.messages.find
                    { it.id == conversationEvent.id }
                )
            }
            is ConversationEvent.UnselectMessage -> {
                uiState.value = uiState.value.copy(
                    selectedMessage = null
                )
            }
        }
    }

    private fun buildMessage(message: String): Message {
        return Message(
            uiState.value.messages.count().toString(),
            MessageDirection.SENT,
            Calendar.getInstance(),
            "me",
            message
        )
    }

    private fun messagesExcluding(id: String): List<Message> {
        return uiState.value.messages
            .toMutableList().apply {
                removeAt(
                    uiState.value.messages.indexOfFirst {
                        it.id == id
                    }
                )
            }.toList()
    }
}