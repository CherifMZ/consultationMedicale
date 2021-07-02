package com.example.consultation.data.data.repositories

import android.content.Context
import android.widget.Toast
import com.example.consultation.constant.sharedPrefFile
import com.example.consultation.data.data.api.RetrofitService
import com.example.consultation.data.data.models.AuthBody
import com.example.consultation.data.data.models.AuthResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthPatientRepository {
    companion object {
        fun authPatient(
            context: Context,
            numero: String,
            motdepasse: String
        ) {
            val authBody = AuthBody(numero, motdepasse)
            val call = RetrofitService.endpoint.authPatient(authBody)

            call.enqueue(object : Callback<AuthResponse> {

                override fun onResponse(
                    call: Call<AuthResponse>,
                    response: Response<AuthResponse>
                ) {
                    if (!response.isSuccessful()) {
                        Toast.makeText(context, "Accès non autorisé", Toast.LENGTH_SHORT).show()
                    } else {
                        val sharedPref = context.getSharedPreferences(
                            sharedPrefFile, Context.MODE_PRIVATE
                        )
                        with(sharedPref?.edit()) {
                            this?.putBoolean("connected",true)
                            this?.putString("role","patient")
                            this?.apply()
                        }
                        Toast.makeText(context, "Accès autorisé", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                    Toast.makeText(context, "Erreur coté serveur", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}