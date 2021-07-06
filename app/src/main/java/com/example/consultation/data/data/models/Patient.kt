package com.example.consultation.data.data.models

import java.util.*

data class Patient(var idPatient: Int, var nom: String, var prenom: String, var photo:String,
                   var numero: String, var motDePasse: String, var birthDate: Date,
                   var email: String, var gender: String, var poids: String,
                   var taille: String, var groupeSanguin : String, var maladie: String)