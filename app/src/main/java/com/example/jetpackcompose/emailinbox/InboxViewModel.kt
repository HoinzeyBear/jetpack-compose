package com.example.jetpackcompose.emailinbox

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class InboxViewModel: ViewModel() {

    val uiState = MutableStateFlow(InboxState())

    fun loadContent() {
        uiState.value = uiState.value.copy(status = InboxStatus.LOADING)

        uiState.value = uiState.value.copy(
            status = InboxStatus.HAS_EMAILS,
            content = EmailFactory.makeEmailList())
    }

    fun handleEvent(inboxEvent: InboxEvent) {
        when (inboxEvent) {
            is InboxEvent.RefreshContent -> loadContent()
            is InboxEvent.DeleteEmail -> deleteEmail(inboxEvent.id)
        }
    }

    private fun deleteEmail(id: String) {
        uiState.value = uiState.value.copy(
            content = uiState.value.content?.filter {
                it.id != id
            })
    }
}