package com.example.jetpackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import com.example.jetpackcompose.authenticationform.Authentication
import com.example.jetpackcompose.homescreen.Home
import com.example.jetpackcompose.messaging.Messaging
import com.example.jetpackcompose.ui.theme.JetpackComposeTheme

@ExperimentalAnimationApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
//                    Settings()
//                    Authentication()
//                    Inbox()
//                    Messaging()
                    Home(modifier = Modifier.fillMaxSize(),
                        orientation = LocalConfiguration.current.orientation)
                }
            }
        }
    }
}

//@Composable
//fun Greeting(name: String) {
//    Text(text = "Hello $name!")
//}

//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    JetpackComposeTheme {
//        Messaging()
//    }
//}