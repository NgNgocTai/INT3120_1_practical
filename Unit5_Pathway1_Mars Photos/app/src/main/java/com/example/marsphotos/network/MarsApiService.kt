package com.example.marsphotos.network

import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

// URL gốc của API
private const val BASE_URL = "https://android-kotlin-fun-mars-server.appspot.com/"

// Tạo Retrofit object (dùng ScalarsConverter để nhận dữ liệu dạng String)
private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

// Định nghĩa endpoint /photos
interface MarsApiService {
    @GET("photos")
    suspend fun getPhotos(): String
}

// Singleton để gọi API ở mọi nơi
object MarsApi {
    val retrofitService: MarsApiService by lazy {
        retrofit.create(MarsApiService::class.java)
    }
}
