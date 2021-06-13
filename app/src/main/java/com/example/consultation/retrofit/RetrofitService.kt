package com.example.consultation.retrofit

import com.example.consultation.constant.url
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitService {
    val endpoint: Endpoint by lazy {
        Retrofit.Builder().baseUrl(url).
                addConverterFactory(GsonConverterFactory.create()).build()
                .create(Endpoint::class.java)

    }
}