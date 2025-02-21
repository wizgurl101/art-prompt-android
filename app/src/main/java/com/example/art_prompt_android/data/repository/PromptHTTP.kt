package com.example.art_prompt_android.data.repository

import android.content.Context
import android.util.Log
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.RetryPolicy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun getPrompt(context: Context, userId: String, token: String): String? {
    val requestQueue = VolleySingleton.getInstance(context).requestQueue
    val url = "http://192.168.1.67:5000/prompt?userId=$userId"

    return withContext(Dispatchers.IO) {
        var result: String? = null
        val request = CustomJsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                result = response.getString("art_prompt")
            },
            {
                result = null
            },
            token
        )

        val retryPolicy: RetryPolicy = DefaultRetryPolicy(
            5000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        request.retryPolicy = retryPolicy

        VolleySingleton.getInstance(context).addToRequestQueue(request)
        while (result == null) {
            // Wait for the response
        }
        result
    }
}