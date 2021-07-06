package com.example.consultation.service

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters
import androidx.work.impl.utils.futures.SettableFuture
import com.auth0.android.jwt.JWT
import com.example.consultation.constant.sharedPrefFile
import com.example.consultation.data.data.api.RetrofitService
import com.example.consultation.data.data.models.AuthResponse
import com.example.consultation.data.data.models.Conseil
import com.example.consultation.data.data.models.RetourConseil
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
        Log.d("contenu",conseil.get(0).conseil)
        return future
    }

    private fun addConseil(conseil: Conseil) {
        val call = RetrofitService.endpoint.demandeConseil(conseil)
        call.enqueue(object:Callback<RetourConseil>{
            override fun onFailure(call: Call<RetourConseil>?, t: Throwable?) {
                future.set(Result.retry())
            }

            override fun onResponse(call: Call<RetourConseil>?, response: Response<RetourConseil>?) {
                if(response?.isSuccessful!!){
                    conseil.isSynchronized=1;
                    RoomService.appDataBase.getConseilDao().updateConseil(conseil)
                    future.set(Result.success())
                }
                else{
                    future.set(Result.retry())
                }
            }


        })

    }
}