package com.example.jetpackcompose.homescreen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Destination(
    val path: String,
    val icon: ImageVector? = null,
    val isRootDestination: Boolean = true) {

    object Home: Destination("home")

    object Feed : Destination("feed", Icons.Default.List)

    object Contacts : Destination(
        "contacts",
        Icons.Default.Person)

    object Calendar : Destination(
        "calendar",
        Icons.Default.DateRange)

    object Settings : Destination("settings",
        Icons.Default.Settings,
        isRootDestination = false)

    object Upgrade : Destination("upgrade",
        Icons.Default.Star,
        isRootDestination = false)

    object Creation: Destination("creation",
        isRootDestination = false)

    object Add: Destination("add",
        icon = Icons.Default.Add,
        isRootDestination = false)

    companion object {
        fun fromString(route: String): Destination {
            return when(route) {
                Feed.path -> Feed
                Calendar.path -> Calendar
                Contacts.path -> Contacts
                Upgrade.path -> Upgrade
                Settings.path -> Settings
                Add.path -> Add
                Creation.path -> Creation
                else -> Home
            }
        }
    }
}
