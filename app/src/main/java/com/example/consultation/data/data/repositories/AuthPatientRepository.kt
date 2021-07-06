package com.example.consultation.data.data.repositories

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.auth0.android.jwt.JWT
import com.example.consultation.constant.sharedPrefFile
import com.example.consultation.data.data.api.RetrofitService
import com.example.consultation.data.data.models.AuthBody
import com.example.consultation.data.data.models.AuthResponse
import com.example.consultation.ui.view.activity.AccueilActivity
import com.example.consultation.ui.view.activity.AffichageMedecinActivity
import com.example.consultation.ui.view.activity.MedecinActivity
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
                        val resp = response.body()

                        if (resp != null) {
                            val userToken = resp.token

                            val jwt = JWT(userToken)
                            val claimID = jwt.getClaim("id") //claimID to have the connected user's ID
                            val claimRole = jwt.getClaim("role")

                            val sharedPref = context.getSharedPreferences(
                                sharedPrefFile, Context.MODE_PRIVATE
                            )
                            with(sharedPref?.edit()) {
                                this?.putBoolean("connected",true)
                                this?.putString("patientID", claimID.asString())
                                this?.putString("userRole", claimRole.asString())
                                this?.putString("token", userToken)
                                this?.apply()
                            }
                        }

                        Toast.makeText(context, "Accès autorisé", Toast.LENGTH_SHORT).show()
                        val myIntent = Intent(context, AccueilActivity::class.java)
                        context.startActivity(myIntent)
                        (context as Activity).finish()
                    }
                }

                override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                    Toast.makeText(context, "Erreur coté serveur", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}