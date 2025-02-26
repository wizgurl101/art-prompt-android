package com.example.art_prompt_android.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import android.net.Uri
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.unit.dp
import com.example.art_prompt_android.utils.getAllPhotos
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.items
import coil.compose.AsyncImage

@Composable
fun PhotoGalleryScreen(onNavigateLogout: () -> Unit, onNavigatePrompt: () -> Unit) {
    val customPrimaryColor = Color(0xFF0C1844)
    val customBackgroundColor = Color(0xFFF7F7F7)
    val context = LocalContext.current
    val photos = remember { mutableStateListOf<Uri>() }

    LaunchedEffect(Unit) {
        photos.addAll(getAllPhotos(context))
    }

    Scaffold(
        bottomBar = {
            BottomAppBar(
            ) {
                IconButton(onClick = onNavigatePrompt) {
                    Icon(
                        imageVector = Icons.Filled.Home,
                        contentDescription = "Prompt Screen"
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
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(customBackgroundColor)
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 128.dp),
                modifier = Modifier.padding(16.dp)
            ) {
                items(photos) { uri ->
                    AsyncImage(
                        model = uri.toString(),
                        contentDescription = null,
                    )
                }
            }
        }
    }
}