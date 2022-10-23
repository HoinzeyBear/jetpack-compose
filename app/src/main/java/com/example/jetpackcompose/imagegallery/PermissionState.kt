package com.example.jetpackcompose.imagegallery

interface PermissionState {

    val permission: String
    val hasPermission: Boolean
    val shouldShowRationale: Boolean
    val permissionRequested: Boolean
    fun launchPermissionRequest() : Unit
}