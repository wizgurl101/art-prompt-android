package com.example.art_prompt_android.ui.viewmodel

//import android.content.Context
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import kotlinx.coroutines.launch
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.setValue
//
//class LoginViewModel(private val context: Context) : ViewModel() {
//    var userId by mutableStateOf<String?>(null)
//    var token by mutableStateOf<String?>(null)
//
//    fun login(email: String, password: String) {
//        viewModelScope.launch {
//            val result = login(context, email, password)
//            result?.let {
//                userId = it.first
//                token = it.second
//            }
//        }
//    }
//}