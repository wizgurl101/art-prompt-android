package com.example.art_prompt_android.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.art_prompt_android.data.repository.getPrompt
import com.example.art_prompt_android.data.repository.login

class PromptViewModel(private val context: Context) : ViewModel() {
    var artPrompt by mutableStateOf<String?>(null)

    fun getArtPrompt(userId: String, token: String) {
        viewModelScope.launch {
            artPrompt = getPrompt(context, userId, token)
        }
    }
}