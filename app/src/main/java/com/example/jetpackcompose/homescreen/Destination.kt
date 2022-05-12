package com.example.jetpackcompose.homescreen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Destination(
    val path: String,
    val icon: ImageVector? = null) {

    object Home: Destination("home")

    object Feed : Destination("feed", Icons.Default.List)

    object Contacts : Destination(
        "contacts",
        Icons.Default.Person)

    object Calendar : Destination(
        "calendar",
        Icons.Default.DateRange)

    object Settings : Destination("settings",
        Icons.Default.Settings)

    object Upgrade : Destination("upgrade",
        Icons.Default.Star)

    companion object {
        fun fromString(route: String): Destination {
            return when(route) {
                Feed.path -> Feed
                Calendar.path -> Calendar
                Contacts.path -> Contacts
                Upgrade.path -> Upgrade
                Settings.path -> Settings
                else -> Home
            }
        }
    }
}
