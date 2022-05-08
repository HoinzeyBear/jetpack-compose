package com.example.jetpackcompose.homescreen

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ContentArea(
    modifier: Modifier = Modifier,
    destination: Destination
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {

        destination.icon?.let { icon ->
            Icon(
                modifier = Modifier.size(80.dp),
                imageVector = icon,
                contentDescription = destination.path
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        Text(
            text = destination.path,
            fontSize = 18.sp
        )

    }
}