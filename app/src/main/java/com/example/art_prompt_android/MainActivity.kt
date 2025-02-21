package com.example.art_prompt_android

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.MaterialTheme
import com.example.art_prompt_android.ui.LoginScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.art_prompt_android.ui.PhotoGalleryScreen
import com.example.art_prompt_android.ui.PromptScreen

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            MaterialTheme {
                NavHost(navController = navController,
                    startDestination = "login",
                    builder = {
                        composable("login") {
                            LoginScreen{
                                navController.navigate("prompt")
                            }
                        }
                        composable("prompt") {
                            PromptScreen(
                                onNavigateLogout = {
                                    navController.navigate("login")
                                },
                                onNavigatePhotoGallery = {
                                    navController.navigate("photo_gallery")
                                }
                            )
                        }
                        composable("photo_gallery") {
                            PhotoGalleryScreen()
                        }
                    })
            }
        }
    }
}