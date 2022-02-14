package com.example.jetpackcompose.settings

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp

@Composable
fun AppVersionSettingItem(
    modifier: Modifier = Modifier,
    title: String,
    description: String
) {
    SettingItem(modifier = modifier) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp)
                .semantics(mergeDescendants = true) {},
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = title
            )
            Text(
                text = description
            )
        }
    }
}