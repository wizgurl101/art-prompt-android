package com.example.art_prompt_android.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.material.icons.Icons
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.example.art_prompt_android.ui.viewmodel.PromptViewModel
import com.example.art_prompt_android.ui.viewmodel.PromptViewModelFactory
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun PromptScreen(userId: String,
                 token: String,
                 onNavigateLogout: () -> Unit,
                 onNavigatePhotoGallery: () -> Unit
) {
    val customPrimaryColor = Color(0xFF0C1844)
    val customBackgroundColor = Color(0xFFF7F7F7)
    val promptViewModel: PromptViewModel = viewModel(factory = PromptViewModelFactory(LocalContext.current))

    LaunchedEffect(Unit) {
        promptViewModel.getArtPrompt(userId, token)
    }

    val dayPrompt = promptViewModel.artPrompt ?: "Draw a tree"
    Log.i("PromptScreen", "Prompt: $dayPrompt")

    Scaffold(
        bottomBar = {
            BottomAppBar(
            ) {
                IconButton(onClick = onNavigatePhotoGallery) {
                    Icon(
                        imageVector = Icons.Filled.AccountBox,
                        contentDescription = "Photo Gallery"
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = onNavigateLogout) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                        contentDescription = "Logout"
                    )
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { /* Handle FAB click */ }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add"
                )
            }
        },
        floatingActionButtonPosition = androidx.compose.material3.FabPosition.Center
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(customBackgroundColor)
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = dayPrompt ?: "Generating prompt...",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 18.sp,
                    color = customPrimaryColor
                )
            )
        }
    }
}