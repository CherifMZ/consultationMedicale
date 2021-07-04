package com.example.consultation.data.data.models

data class RDVResponse(var Encours: String, var idRdv : Int, var idMedecin : Int,
                       var idPatient : Int, var dateRdv : String, var heureRdv : String,
                       var heureFinEstimee : String)
