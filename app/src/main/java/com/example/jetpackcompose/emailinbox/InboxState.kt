package com.example.jetpackcompose.emailinbox

data class InboxState(
    val status: InboxStatus = InboxStatus.LOADING,
    val content: List<EmailEntity>? = null
)
