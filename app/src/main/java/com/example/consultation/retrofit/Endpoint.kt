package com.example.consultation.retrofit

import com.example.consultation.data.model.Medecin
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface Endpoint {

    @GET("Medecin")
    fun getAllMedecins():Call<List<Medecin>>

}