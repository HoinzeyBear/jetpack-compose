package com.example.jetpackcompose.homescreen

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.jetpackcompose.R
import kotlinx.coroutines.launch

@Composable
fun Home(modifier: Modifier = Modifier,
         orientation: Int) {

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState(drawerState)
    val navController = rememberNavController()
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentDestination by derivedStateOf {
        navBackStackEntry.value?.destination?.route?.let {
            Destination.fromString(it)
        } ?: run {
            Destination.Home
        }
    }

        Scaffold(modifier = modifier,
        scaffoldState = scaffoldState,
        topBar = {
            DestinationTopBar(
                destination = currentDestination,
                onNavigateUp = { navController.popBackStack() },
                onOpenDrawer = {
                    coroutineScope.launch { drawerState.open() }
                },
                showSnackbar = {
                    coroutineScope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(it)
                    }
                }
            )
        },
        floatingActionButton = {
//            if (orientation != Configuration.ORIENTATION_LANDSCAPE &&
//                Destination.Feed.path == navController.currentDestination?.route) {
            if(orientation != Configuration.ORIENTATION_LANDSCAPE &&
                currentDestination == Destination.Feed){
                FloatingActionButton(onClick = {
                    navController.navigate(Destination.Creation)
                }) {
                    Icon(imageVector = Icons.Default.Add,
                        contentDescription = stringResource(R.string.cd_create_item))
                }
            }
        },
        bottomBar = {
            if (currentDestination.isRootDestination &&
                orientation != Configuration.ORIENTATION_LANDSCAPE) {
                BottomNavigationBar(
                    currentDestination = currentDestination,
                    onNavigate = {
                        navController.navigate(it.path) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    })
            }
        },
        drawerContent = {
            DrawerContent(modifier = Modifier.fillMaxWidth(),
            onNavigationSelected = {
                navController.navigate(it.path)
                coroutineScope.launch { drawerState.close() }
            },
            onLogout = {})
        }) {

            Body(
                modifier = Modifier.fillMaxSize(),
                destination = currentDestination,
                orientation = orientation,
                navController = navController,
                onCreateItem = {
                    navController.navigate(Destination.Add.path)
                },
                onNavigate = {
                    navController.navigate(it.path) {
                        popUpTo(Destination.Home.path) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )

        }
}

@Composable
fun Body(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    destination: Destination,
    orientation: Int,
    onCreateItem: () -> Unit,
    onNavigate: (destination: Destination) -> Unit
) {
    Row(modifier = modifier) {
        if (destination.isRootDestination &&
            orientation == Configuration.ORIENTATION_LANDSCAPE) {
            RailNavigationBar(
                currentDestination = destination,
                onCreateItem = onCreateItem,
                onNavigate = onNavigate
            )
        }
        Navigation(
            modifier = Modifier.fillMaxSize(),
            navController = navController)
    }
}

fun NavController.navigate(destination: Destination) {
    navigate(destination.path)
}

@Preview
@Composable
fun HomePreview() {
    MaterialTheme {
        Home(modifier = Modifier.fillMaxSize(),
            Configuration.ORIENTATION_PORTRAIT)
    }
}