package de.philipphager.ursula.network

import okhttp3.OkHttpClient
import okhttp3.Request

class HttpClient(private val client: OkHttpClient) {

    fun get(url: String): String {
        val request = Request.Builder()
                .url(url)
                .build()

        val response = client.newCall(request).execute()
        return response.body()?.string() ?: ""
    }
}
