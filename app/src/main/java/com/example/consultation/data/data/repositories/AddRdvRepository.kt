package com.example.consultation.data.data.repositories

import android.content.Context
import android.widget.Toast
import com.example.consultation.constant.sharedPrefFile
import com.example.consultation.data.data.api.RetrofitService
import com.example.consultation.data.data.models.RDVBody
import com.example.consultation.data.data.models.RDVResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddRdvRepository {
    companion object {
        fun addRDV(
            context: Context,
            idMedecin : Int,
            idPatient : Int,
            dateRdv : String,
            heureRdv : String,
            heureFinEstimee : String
        ) {
            val rdvBody = RDVBody(idMedecin, idPatient, dateRdv, heureRdv, heureFinEstimee)
            val call = RetrofitService.endpoint.addRDV(rdvBody)

            call.enqueue(object : Callback<RDVResponse> {

                override fun onResponse(
                    call: Call<RDVResponse>,
                    response: Response<RDVResponse>
                ) {
                    if (!response.isSuccessful()) {
                        Toast.makeText(context, "Choisissez une autre date/heure", Toast.LENGTH_SHORT).show()
                    } else {
                        val resp = response.body()

                        if (resp != null) {
                            val sharedPref = context.getSharedPreferences(
                                sharedPrefFile, Context.MODE_PRIVATE
                            )
                            with(sharedPref?.edit()) {
                                this?.putInt("idRDV", resp.idRdv)
                                this?.apply()
                            }
                        }
                        Toast.makeText(context, "Votre Rendez-Vous a bien été enregistré", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<RDVResponse>, t: Throwable) {
                    Toast.makeText(context, "Erreur coté serveur", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}