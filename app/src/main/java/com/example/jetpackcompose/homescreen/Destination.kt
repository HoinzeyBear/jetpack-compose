package com.example.jetpackcompose.homescreen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Destination(
    val path: String,
    val icon: ImageVector? = null) {

    object Home: Destination("home")

    object Feed : Destination("feed", Icons.Default.List)

    object Contacts : Destination(
        "contacts",
        Icons.Default.Person
    )

    object Calendar : Destination(
        "calendar",
        Icons.Default.DateRange
    )

    companion object {
        fun fromString(route: String): Destination {
            return when(route) {
                Feed.path -> Feed
                Calendar.path -> Calendar
                Contacts.path -> Contacts
                else -> Home
            }
        }
    }
}
