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

class GetRDVRepository {
    companion object {
        fun getRDV(
                context: Context,
                id : Int
        ) {
            val call =  RetrofitService.endpoint.getRDV(id)
            val sharedPref = context.getSharedPreferences(
                    sharedPrefFile, Context.MODE_PRIVATE
            )

            call.enqueue(object : Callback<RDVResponse> {

                override fun onResponse(
                        call: Call<RDVResponse>,
                        response: Response<RDVResponse>
                ) {
                    if (!response.isSuccessful()) {
                        Toast.makeText(context, "Can't get data", Toast.LENGTH_SHORT).show()
                    } else {
                        val resp = response.body()

                        if (resp != null) {
                            with(sharedPref?.edit()) {
                                this?.putString("date", resp.dateRdv)
                                this?.putString("heure", resp.heureRdv)
                                this?.putString("heureEst", resp.heureFinEstimee)
                                this?.putInt("getRDVPatientId", resp.idPatient)
                                this?.apply()
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<RDVResponse>, t: Throwable) {
                    Toast.makeText(context, "Erreur coté serveur", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}