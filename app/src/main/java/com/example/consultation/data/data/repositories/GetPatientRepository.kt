package com.example.consultation.data.data.repositories

import android.content.Context
import android.widget.Toast
import com.example.consultation.constant.sharedPrefFile
import com.example.consultation.data.data.api.RetrofitService
import com.example.consultation.data.data.models.Patient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetPatientRepository {
    companion object{
        fun getP(
            context: Context,
            id : Int
        ) {
            val call = RetrofitService.endpoint.getPatient(id)
            call.enqueue(object : Callback<Patient> {

                override fun onResponse(
                    call: Call<Patient>,
                    response: Response<Patient>
                ) {
                    if (!response.isSuccessful()) {
                        Toast.makeText(context, "Erreur dans la récuperation du Patient", Toast.LENGTH_SHORT).show()
                    } else {
                        val resp = response.body()

                        if (resp != null) {

                            val sharedPref = context.getSharedPreferences(
                                sharedPrefFile, Context.MODE_PRIVATE
                            )
                            with(sharedPref?.edit()) {
                                this?.putString("nomP", resp.nom)
                                this?.putString("prenomP", resp.prenom)
                                this?.putString("groupeSanguin", resp.groupeSanguin)
                                this?.putString("numP", resp.numero)
                                this?.apply()
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<Patient>, t: Throwable) {
                    Toast.makeText(context, "Erreur coté serveur", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}