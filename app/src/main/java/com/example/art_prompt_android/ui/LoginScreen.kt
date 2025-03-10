package com.example.art_prompt_android.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.example.art_prompt_android.R
import com.example.art_prompt_android.ui.viewmodel.AuthViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.art_prompt_android.ui.viewmodel.AuthViewModelFactory

@Composable
fun LoginScreen(onLoginSuccess: (String, String) -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val customPrimaryColor = Color(0xFF0C1844)
    val customBackgroundColor = Color(0xFFF7F7F7)
    val context = LocalContext.current
    val authViewModel: AuthViewModel = viewModel(factory = AuthViewModelFactory(context))
    var loginFailed by remember { mutableStateOf(false) }
    var didUserSignIn by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(customBackgroundColor),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    )
    {

        ComposableRiveAnimationView(
            modifier = Modifier.size(320.dp),
            animation = R.raw.art_prompt_title,
            onInit = {
                it.play()
            }
        )

        OutlinedTextField(value = email,
            onValueChange = {
                email = it
            },
            label = {
                Text(text = "Email")
            })

        Spacer(modifier = Modifier.height(4.dp))

        OutlinedTextField(value = password,
            onValueChange = {
                password = it
            },
            label = {
                Text(text = "Password")
            },
            visualTransformation = PasswordVisualTransformation(),
        )

        Spacer(modifier = Modifier.height(6.dp))

        Button(
            onClick = {
            didUserSignIn = true
            loginFailed = false
            authViewModel.getCredentials(email, password)
        },
            colors = ButtonDefaults.buttonColors(
                containerColor = customPrimaryColor
            )) {
            Text(text = "Login")
        }

        if(loginFailed) {
            Text(
                text = "Login failed. Please try again.",
                color = Color.Red,
                modifier = Modifier.padding(8.dp)
            )
        }
    }

    LaunchedEffect(authViewModel.userId, authViewModel.token) {
        if (authViewModel.userId != null && authViewModel.token != null && didUserSignIn) {
            onLoginSuccess(authViewModel.userId!!, authViewModel.token!!)
        } else {
            if(didUserSignIn) {
                loginFailed = true
            }
        }
    }
}