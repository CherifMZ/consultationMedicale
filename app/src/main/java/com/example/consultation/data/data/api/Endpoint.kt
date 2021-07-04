package com.example.consultation.data.data.api

import com.example.consultation.data.data.models.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface Endpoint {

    @GET("/Medecin")
    fun getAllMedecins():Call<List<Medecin>>

    @POST("/auth/patient")
    fun authPatient(@Body authBody: AuthBody) : Call<AuthResponse>

    @POST("/auth/medecin")
    fun authMedecin(@Body authBody: AuthBody) : Call<AuthResponse>

    @POST("/rendezVous")
    fun addRDV(@Body addRDVBody: RDVBody) : Call<RDVResponse>
}