package com.example.art_prompt_android.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PromptViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PromptViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PromptViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}