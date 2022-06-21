package com.example.jetpackcompose.homescreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun Navigation(
    modifier: Modifier = Modifier,
    navController: NavHostController) {

    NavHost(navController = navController,
            modifier = modifier,
            startDestination = Destination.Home.path) {

        navigation(startDestination = Destination.Feed.path, route = Destination.Home.path){
            composable(Destination.Feed.path) {
                ContentArea(Modifier.fillMaxSize(), destination = Destination.Feed)
            }

            composable(Destination.Contacts.path) {
                ContentArea(Modifier.fillMaxSize(), destination = Destination.Contacts)
            }

            composable(Destination.Calendar.path) {
                ContentArea(Modifier.fillMaxSize(), destination = Destination.Calendar)
            }
        }

        composable(Destination.Settings.path) {
            ContentArea(Modifier.fillMaxSize(), destination = Destination.Settings)
        }

        composable(Destination.Upgrade.path) {
            ContentArea(Modifier.fillMaxSize(), destination = Destination.Upgrade)
        }

        navigation(
            startDestination = Destination.Add.path,
            route = Destination.Creation.path
        ) {
            composable(route = Destination.Add.path) {
                ContentArea(
                    modifier = Modifier.fillMaxSize(),
                    destination = Destination.Add
                )
            }
        }
    }
}

public fun NavGraphBuilder.composable(
    route: String,
    arguments: List<NamedNavArgument> = emptyList(),
    deepLinks: List<NavDeepLink> = emptyList(),
    content: @Composable (NavBackStackEntry) -> Unit){}
