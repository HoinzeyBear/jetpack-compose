package com.example.jetpackcompose.homescreen

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    currentDestination: Destination,
    onNavigate: (destination: Destination) -> Unit) {

    BottomNavigation(modifier = modifier) {
        listOf(
            Destination.Feed,
            Destination.Contacts,
            Destination.Calendar
        ).forEach {
            BottomNavigationItem(
                selected = currentDestination.path == it.path,
                icon = {
                    it.icon?.let { image ->
                        Icon(imageVector = it.icon, contentDescription = it.path)
                    }
                },
                onClick = { onNavigate(it) },
                label = { Text(text = it.path) }
            )

        }
    }
}

@Preview
@Composable
fun previewNavBar() {
    BottomNavigationBar(
        currentDestination = Destination.Contacts,
        onNavigate = {})
}