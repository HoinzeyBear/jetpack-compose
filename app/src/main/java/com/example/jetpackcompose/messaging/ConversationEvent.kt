package com.example.jetpackcompose.messaging

sealed class ConversationEvent {

    data class SendMessage(val message: String): ConversationEvent()

    class UnsendMessage(val id: String): ConversationEvent()

    class SelectMessage(val id: String): ConversationEvent()

    object UnselectMessage: ConversationEvent()
}
