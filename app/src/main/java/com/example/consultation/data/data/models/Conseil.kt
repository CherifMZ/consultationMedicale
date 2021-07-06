package com.example.consultation.data.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "conseils")
data class Conseil(var idMedecin:Int,
                   var idPatient:Int,
                   var conseil:String,
                   var isSynchronized:Int =0
) : Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "conseil_id")
    var conseilID:Int?=null
}
