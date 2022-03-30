package com.example.jetpackcompose.messaging

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import com.example.jetpackcompose.R
import com.example.jetpackcompose.messaging.model.Contact
import com.example.jetpackcompose.messaging.model.Mentions
import com.example.jetpackcompose.shared.buildAnnotatedStringWithColor
import com.example.jetpackcompose.shared.inputShouldTriggerSuggestions
import com.example.jetpackcompose.shared.selectedWord

@Composable
fun InputBar(
    modifier: Modifier = Modifier,
    sendMessage: (message: String) -> Unit,
    contacts: List<Contact>
) {

    var textState by remember { mutableStateOf(TextFieldValue()) }
    val showMentions by derivedStateOf { textState.text.isNotEmpty()
            && inputShouldTriggerSuggestions(contacts, selectedWord(textState))}

    Column(modifier = modifier) {
        if(showMentions) {
            Mentions(modifier = Modifier.fillMaxWidth(), contacts, selectedWord(textState)) { query,result ->
                val startIndex = textState.text.indexOf(query)
                textState = textState.replaceText(
                    startIndex,
                    startIndex + query.length, result)
            }
        }

        Divider()
        TextField(modifier = Modifier.fillMaxWidth(),
            value = textState,
            visualTransformation = MentionHighlightTransformation(MaterialTheme.colors.primary),
            onValueChange = { textState = it },
            placeholder = {
                Text(
                    text = stringResource(id = R.string.hint_send_message),
                    color = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.medium))
            },
            colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.surface),
        trailingIcon = {
            IconButton(enabled = textState.text.isNotEmpty(),
                onClick = {
                sendMessage(textState.text)
                textState = TextFieldValue()
            }) {
                Icon(imageVector = Icons.Default.Send, contentDescription = stringResource(id = R.string.cd_send_message),
                tint = if(textState.text.isNotEmpty()){
                    MaterialTheme.colors.primary
                } else {
                    MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.disabled)
                })
            }
        })
    }
}

fun TextFieldValue.replaceText(
    start: Int,
    end: Int,
    replaceWith: String): TextFieldValue {
    val newText = this.text.replaceRange(
        start,
        end,
        replaceWith
    )
    val endOfNewWord = start + replaceWith.length
    val range = TextRange(endOfNewWord, endOfNewWord)
    return this.copy(text = newText, selection = range)
}

class MentionHighlightTransformation(private val color: Color): VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        return TransformedText(
            buildAnnotatedStringWithColor(text.toString(), color), OffsetMapping.Identity)
    }
}

//@Preview
//@Composable
//fun preview_inpubar() {
//    InputBar(sendMessage = {})
//}