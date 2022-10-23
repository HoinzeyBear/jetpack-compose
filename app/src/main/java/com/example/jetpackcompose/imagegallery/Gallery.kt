@file:OptIn(ExperimentalPermissionsApi::class, ExperimentalFoundationApi::class)

package com.example.jetpackcompose.imagegallery

import android.Manifest
import android.annotation.SuppressLint
import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState

@ExperimentalFoundationApi
@Composable
fun Gallery() {
    val permissionState = rememberPermissionState(
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

    MaterialTheme{
        GalleryContent(
            modifier = Modifier.fillMaxSize(),
            permissionState = permissionState
        )
    }
}

@SuppressLint("PermissionLaunchedDuringComposition")
@Composable
fun GalleryContent(
    modifier: Modifier = Modifier,
    permissionState: com.google.accompanist.permissions.PermissionState
) {
    when {
        !permissionState.permissionRequested -> {
            permissionState.launchPermissionRequest()
        }
    }
}

@Composable
fun ImageGallery(
    modifier: Modifier = Modifier,
    images: List<Image>
) {

    var selectedImage by remember {
        mutableStateOf<Uri?>(null)
    }

    Box(contentAlignment = Alignment.Center) {
        LazyVerticalGrid(
            modifier = modifier,
            cells = GridCells.Fixed(2)) {
            items(images) { image ->
                GalleryImage(
                    modifier = Modifier
                        .height(150.dp)
                        .fillMaxWidth()
                        .clickable {
                            selectedImage = image.uri
                        },
                    uri = image.uri,
                    scaleType = ContentScale.Crop)
            }
        }
        selectedImage?.let {
            GalleryPreview(
                selectedImage = selectedImage!!,
                modifier = Modifier.fillMaxSize())
        }
    }
}

@Composable
fun GalleryPreview(
    modifier: Modifier = Modifier,
    selectedImage: Uri
) {
    Box(
        modifier = modifier.background(Color.Black)) {
        GalleryImage(
            uri = selectedImage,
            scaleType = ContentScale.None)
    }
}

@Composable
fun GalleryImage(
    modifier: Modifier = Modifier,
    uri: Uri,
    scaleType: ContentScale) {

    Image(
        modifier = modifier,
        painter = rememberImagePainter(
            data = uri,
        builder = {
            crossfade(true)
//            placeholder(painterResource(id = R.dr))
//            error()
            }),
        contentDescription = null,
        contentScale = scaleType
    )
}