package com.example.jetpackcompose.shared

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.withStyle
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

fun buildAnnotatedStringWithColor(
    text: String,
    color: Color
): AnnotatedString {
    val words: List<String> = text.split(" ")
    val builder = AnnotatedString.Builder()
    words.forEachIndexed { index, word ->
        if (word.startsWith("@")) {
            builder.withStyle(
                style = SpanStyle(color = color)
            ) {
                append(word)
            }
        } else {
            builder.append(word)
        }
        if (index < words.count() - 1) builder.append(" ")
    }
    return builder.toAnnotatedString()
}