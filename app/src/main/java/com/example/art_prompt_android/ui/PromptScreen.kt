package com.example.art_prompt_android.ui

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.provider.MediaStore
import android.Manifest
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.outlined.AddAPhoto
import androidx.compose.material.icons.outlined.Image
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.art_prompt_android.ui.viewmodel.PromptViewModel
import com.example.art_prompt_android.ui.viewmodel.PromptViewModelFactory
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.art_prompt_android.utils.savePhotoToMediaStore
import kotlinx.coroutines.launch

@Composable
fun PromptScreen(userId: String,
                 token: String,
                 onNavigateLogout: () -> Unit,
                 onNavigatePhotoGallery: () -> Unit
) {
    val customPrimaryColor = Color(0xFF0C1844)
    val customBackgroundColor = Color(0xFFF7F7F7)
    val context = LocalContext.current
    val promptViewModel: PromptViewModel = viewModel(factory = PromptViewModelFactory(context))
    val coroutineScope = rememberCoroutineScope()
    var capturedImage by remember { mutableStateOf<Bitmap?>(null) }
    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        promptViewModel.getArtPrompt(userId, token)
    }

    val dayPrompt = promptViewModel.artPrompt ?: "Generating prompt..."

    val takePictureLauncher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val photo = result.data?.extras?.get("data") as Bitmap
            capturedImage = photo
            showDialog = true
            coroutineScope.launch {
                val resultSaved = savePhotoToMediaStore(context, capturedImage!!)
                Log.i("PhotoSaved", "Photo saved to: $resultSaved")
            }
        }
    }

    fun requestCameraPermission() {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(context as Activity, arrayOf(Manifest.permission.CAMERA), 0)
        } else {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            takePictureLauncher.launch(cameraIntent)
        }
    }

    Scaffold(
        bottomBar = {
            BottomAppBar(
            ) {
                IconButton(onClick = onNavigatePhotoGallery) {
                    Icon(
                        imageVector = Icons.Outlined.Image,
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
            FloatingActionButton(onClick = {
                requestCameraPermission()
            }) {
                Icon(
                    imageVector = Icons.Outlined.AddAPhoto,
                    contentDescription = "Take a photo"
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