package com.example.consultation.data.data.models
import java.io.Serializable
import java.util.*

data class Medecin(val idMedecin: Int,
                   val photo:String,
                   var nom: String,
                   val prenom: String,
                   val numero: String,
                   val motDePasse: String,
                   val email: String,
                   val specialite : String,
                   val latitude: Double,
                   val longitude: Double,
                   val birthDate:Date,
                   val gender:String,
                   val experience:Int
):Serializable