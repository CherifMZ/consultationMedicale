package com.example.consultation.data.data.api

import com.example.consultation.data.data.models.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface Endpoint {

    @GET("/Medecin")
    fun getAllMedecins():Call<List<Medecin>>

    @POST("/auth/patient")
    fun authPatient(@Body authBody: AuthBody) : Call<AuthResponse>

    @POST("/auth/medecin")
    fun authMedecin(@Body authBody: AuthBody) : Call<AuthResponse>

    @POST("/rendezVous")
    fun addRDV(@Body addRDVBody: RDVBody) : Call<RDVResponse>

    @GET("/rendezVous/{id}")
    fun getRDV(@Path("id") id: Int): Call<RDVResponse>

    @POST("/conseil")
    fun demandeConseil(@Body info: Conseil):Call<String>

    @GET("/traitement/{id}")
    fun getTreatment(@Path("id") id: Int): Call<List<TreatmentResponse>>

    @GET("/patient/{id}")
    fun getPatient(@Path("id") id: Int): Call<Patient>

}