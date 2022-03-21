package com.example.jetpackcompose.messaging.model


data class ConversationState(
    val messages: List<Message> = MessageFactory.makeMessages(),
    val contacts: List<Contact> = Contact.makeContacts(),
    val selectedMessage: Message? = null
)