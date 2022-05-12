package com.example.jetpackcompose.homescreen

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
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.jetpackcompose.R
import kotlinx.coroutines.launch

@Composable
fun Home(modifier: Modifier = Modifier) {

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
            val snackbarMessage = stringResource(
                id = R.string.not_available_yet)
            TopAppBar(
                title = {
                    Text(text = "Home")
                },
            actions = {
                if(currentDestination != Destination.Feed) {
                    IconButton(onClick = {
                        coroutineScope.launch {
                            scaffoldState.snackbarHostState.showSnackbar(snackbarMessage)
                        }
                    }){
                        Icon(imageVector = Icons.Default.Info,
                        contentDescription = stringResource(id = R.string.cd_more_information))
                    }
                }
            },
            navigationIcon = {
                IconButton(onClick = {
                    coroutineScope.launch {
                        drawerState.open()
                    }
                }){
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = stringResource(
                            id = R.string.cd_open_menu)
                    )
                }
            })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {}) {
                //Fab body
                Icon(imageVector = Icons.Default.Add,
                contentDescription = stringResource(R.string.cd_create_item))
            }
        },
        bottomBar = {
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
        },
        drawerContent = {
            DrawerContent(modifier = Modifier.fillMaxWidth(),
            onNavigationSelected = {
                navController.navigate(it.path)
                coroutineScope.launch { drawerState.close() }
            },
            onLogout = {})
        }) {
        //Scaffold body
        Navigation(modifier = modifier, navController = navController)
    }
}

@Preview
@Composable
fun HomePreview() {
    MaterialTheme {
        Home(modifier = Modifier.fillMaxSize())
    }
}