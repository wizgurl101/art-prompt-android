package com.example.art_prompt_android

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import com.example.art_prompt_android.ui.LoginScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.art_prompt_android.ui.PhotoGalleryScreen
import com.example.art_prompt_android.ui.PromptScreen
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            var userId by remember { mutableStateOf<String?>(null) }
            var token by remember { mutableStateOf<String?>(null) }

            MaterialTheme {
                NavHost(navController = navController,
                    startDestination = "login",
                    builder = {
                        composable("login") {
                            LoginScreen { id, tkn ->
                                userId = id
                                token = tkn
                                navController.navigate("prompt")
                            }
                        }
                        composable("prompt") {
                            if (userId != null && token != null) {
                                PromptScreen(
                                    userId = userId!!,
                                    token = token!!,
                                    onNavigateLogout = {
                                        navController.navigate("login")
                                    },
                                    onNavigatePhotoGallery = {
                                        navController.navigate("photo_gallery")
                                    }
                                )
                            }
                        }
                        composable("photo_gallery") {
                            PhotoGalleryScreen(
                                onNavigateLogout = {
                                    navController.navigate("login")
                                },
                                onNavigatePrompt = {
                                    navController.navigate("prompt")
                                }
                            )
                        }
                    })
            }
        }
    }
}