package com.example.art_prompt_android.data.repository

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

suspend fun login(context: Context, email: String, password: String): Pair<String, String>? {
    val requestQueue = VolleySingleton.getInstance(context).requestQueue
    val url = "http://localhost:5000/login"
    val requestBody = JSONObject().apply {
        put("email", email)
        put("password", password)
    }

    return withContext(Dispatchers.IO) {
        var result: Pair<String, String>? = null
        val request = JsonObjectRequest(
            Request.Method.POST, url, requestBody,
            { response ->
                val userId = response.getString("userId")
                val token = response.getString("token")
                result = Pair(userId, token)
            },
            {
                result = null
            }
        )

        VolleySingleton.getInstance(context).addToRequestQueue(request)
        while (result == null) {
            // Wait for the response
        }
        result
    }
}