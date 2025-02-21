package com.example.art_prompt_android.data.repository

import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONObject

class CustomJsonObjectRequest(
    method: Int,
    url: String,
    jsonRequest: JSONObject?,
    listener: Response.Listener<JSONObject>,
    errorListener: Response.ErrorListener,
    private val token: String
) : JsonObjectRequest(method, url, jsonRequest, listener, errorListener) {

    override fun getHeaders(): Map<String, String> {
        val headers = HashMap<String, String>()
        headers["Authorization"] = "Bearer $token"
        return headers
    }
}