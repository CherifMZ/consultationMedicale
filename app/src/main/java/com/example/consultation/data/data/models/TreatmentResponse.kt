package com.example.consultation.data.data.models

data class TreatmentResponse(val idPatient : Int, val idMedecin : Int,
                             val nom : String, val duree : Int,
                             val prise : Int, val etat : String)
