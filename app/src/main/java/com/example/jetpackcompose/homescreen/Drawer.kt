package com.example.jetpackcompose.homescreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.util.*

@Composable
fun DrawerItem(
    modifier: Modifier = Modifier,
    label: String,
    onClick: () -> Unit){

    Text(
        modifier = modifier.clickable { onClick() }
            .padding(15.dp),
        text = label.replaceFirstChar {
            it.titlecase(Locale.getDefault())
        })
}