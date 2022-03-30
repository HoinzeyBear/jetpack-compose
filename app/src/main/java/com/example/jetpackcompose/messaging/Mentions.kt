package com.example.jetpackcompose.messaging.model

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.jetpackcompose.R
import com.example.jetpackcompose.shared.stripMentionSymbol

@Composable
fun Mentions(
    modifier: Modifier = Modifier,
    contacts: List<Contact>,
    query: String?,
    onMentionClicked: (query: String,mention: String) -> Unit) {

    val mentions = contacts.filter {
        val withoutMentionSymbol = stripMentionSymbol(query?.lowercase())
        it.name.lowercase().startsWith(withoutMentionSymbol)
    }
    val clickLabel = stringResource(id = R.string.cd_mention_contact)
    Column(modifier = modifier) {
        Divider()
        LazyRow(modifier = modifier) {
            items(mentions) { contact ->
                Text(
                    modifier = Modifier
                        .clickable(onClickLabel = clickLabel) {
                            onMentionClicked(query!! ,"@${contact.name}")
                        }
                        .padding(16.dp),
                    text = contact.name)
            }
        }
    }
}