package com.example.jetpackcompose.shared

import androidx.compose.ui.text.input.TextFieldValue
import com.example.jetpackcompose.messaging.model.Contact

fun stripMentionSymbol(text: String?) =
    text?.replace("@", "") ?: ""

fun selectedWord(textState: TextFieldValue): String? {
    val startSelection = textState.selection.start
    var position = 0
    for (currentWord in textState.text.split(" ")) {
        position += currentWord.length + 1
        if (position >= startSelection) return currentWord
    }
    return null
}

fun inputShouldTriggerSuggestions(contacts: List<Contact>, query: String?) =
    query != null && query.startsWith('@') && !contacts.any { it.name == stripMentionSymbol(query) }