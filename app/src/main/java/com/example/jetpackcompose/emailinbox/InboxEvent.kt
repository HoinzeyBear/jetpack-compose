package com.example.jetpackcompose.emailinbox

sealed class InboxEvent {

    object RefreshContent: InboxEvent()

    class DeleteEmail(val id: String): InboxEvent()
}