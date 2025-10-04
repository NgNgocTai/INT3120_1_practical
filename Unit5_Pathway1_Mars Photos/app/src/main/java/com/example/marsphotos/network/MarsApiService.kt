package com.example.marsphotos.network

import retrofit2.Retrofit
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType

import retrofit2.http.GET

// URL gốc của API
private const val BASE_URL =
    "https://android-kotlin-fun-mars-server.appspot.com/"
private val retrofit = Retrofit.Builder()
    .addConverterFactory(
        Json {
            ignoreUnknownKeys = true
        }.asConverterFactory("application/json".toMediaType())
    )
    .baseUrl(BASE_URL)
    .build()

// Định nghĩa endpoint /photos
interface MarsApiService {
    @GET("photos")
    suspend fun getPhotos(): List<MarsPhoto>
}

// Singleton để gọi API ở mọi nơi
object MarsApi {
    val retrofitService: MarsApiService by lazy {
        retrofit.create(MarsApiService::class.java)
    }
}
