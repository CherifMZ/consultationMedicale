package com.example.consultation.data.model
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity(tableName = "medecins")

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
):Serializable{

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "medecin_id")
    var medecinID:Int?=null
}