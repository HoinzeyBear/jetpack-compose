package com.example.jetpackcompose.homescreen

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import java.util.*

@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    currentDestination: Destination,
    onNavigate: (destination: Destination) -> Unit) {

    BottomNavigation(modifier = modifier) {

        buildNavigationBarItems(
            currentDestination = currentDestination,
            onNavigate = onNavigate
        ).forEach {
            BottomNavigationItem(
                selected = it.selected,
                onClick = it.onClick,
                icon =  it.icon,
                label = it.label)
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

fun buildNavigationBarItems(
    currentDestination: Destination,
    onNavigate: (destination: Destination) -> Unit): List<NavigationBarItem> {

    return listOf(
        Destination.Feed,
        Destination.Contacts,
        Destination.Calendar
    ).map {
        NavigationBarItem(
            label = {
                Text(text = it.path.replaceFirstChar { char ->
                    char.titlecase(Locale.getDefault())
                })
            },
            icon = {
                it.icon?.let { image ->
                    Icon(
                        imageVector = image,
                        contentDescription = null
                    )
                }
            },
            selected = currentDestination.path == it.path,
            onClick = {
                onNavigate(it)
            }
        )
    }
}