package com.example.jetpackcompose.homescreen

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.jetpackcompose.R

@Composable
fun DestinationTopBar(
    modifier: Modifier = Modifier,
    destination: Destination,
    onNavigateUp: () -> Unit,
    onOpenDrawer: () -> Unit,
    showSnackbar: (message: String) -> Unit) {

    if (destination.isRootDestination) {
        RootDestinationTopBar(
            modifier,
            openDrawer = onOpenDrawer,
            showSnackbar = showSnackbar,
            currentDestination = destination
        )
    } else {
        ChildDestinationTopBar(
            modifier,
            destination.path,
            onNavigateUp
        )
    }
}

@Composable
fun RootDestinationTopBar(
    modifier: Modifier = Modifier,
    currentDestination: Destination,
    openDrawer: () -> Unit,
    showSnackbar: (message: String) -> Unit) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(text = Destination.Home.path)
        },
        navigationIcon = {
            IconButton(onClick = {
                openDrawer()
            }) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription =
                        stringResource(id = R.string.cd_open_menu)
                )
            }
        },
        actions = {
            if (currentDestination != Destination.Feed) {
                val snackbarMessage = stringResource(
                    id = R.string.not_available_yet)
                IconButton(onClick = {
                    showSnackbar(snackbarMessage)
                }) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = stringResource(
                            id = R.string.cd_more_information))
                }
            }
        })
}

@Composable
fun ChildDestinationTopBar(
    modifier: Modifier = Modifier,
    title: String,
    onNavigateUp: () -> Unit) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(text = title)
        },
    navigationIcon = {
        IconButton(onClick = {
            onNavigateUp()
        }){
            Icon(imageVector = Icons.Default.ArrowBack,
            contentDescription = stringResource(id = R.string.cd_navigate_up))
        }
    })
}


