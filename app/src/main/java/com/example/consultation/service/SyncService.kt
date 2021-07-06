package com.example.consultation.service

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters
import androidx.work.impl.utils.futures.SettableFuture
import com.auth0.android.jwt.JWT
import com.example.consultation.constant.sharedPrefFile
import com.example.consultation.data.data.api.RetrofitService
import com.example.consultation.data.data.models.AuthResponse
import com.example.consultation.data.data.models.Conseil
import com.example.consultation.data.data.models.TreatmentResponse
import com.example.consultation.roomDao.RoomService
import com.example.consultation.roomDao.RoomService.context
import com.example.consultation.ui.view.activity.AffichageMedecinActivity
import com.google.common.util.concurrent.ListenableFuture
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@SuppressLint("RestrictedApi")
class SyncService(val ctx: Context, val workParamters: WorkerParameters):
    ListenableWorker(ctx, workParamters) {

    lateinit var  future: SettableFuture<Result>

    override fun startWork(): ListenableFuture<Result> {

        future = SettableFuture.create()
        val conseil = RoomService.appDataBase.getConseilDao().getConseilToSynchronize()
        addConseil(conseil.get(0))
        return future
    }

    private fun addConseil(conseil: Conseil) {
        val call = RetrofitService.endpoint.demandeConseil(conseil)

        call.enqueue(object : Callback<String> {

            override fun onResponse(
                call: Call<String>,
                response: Response<String>
            ) {
                if (!response.isSuccessful()) {
                    Toast.makeText(context, "Erreur", Toast.LENGTH_SHORT).show()
                    future.set(Result.retry())
                } else {
                    val resp = response.body()
                    if (resp != null) {
                        RoomService.context = context
                        conseil.isSynchronized=1;
                        RoomService.appDataBase.getConseilDao().updateConseil(conseil)
                        future.set(Result.success())
                    }
                    Toast.makeText(context, "Ajouté", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Toast.makeText(context, "Erreur coté serveur", Toast.LENGTH_SHORT).show()
                future.set(Result.retry())
            }
        })
    }
}