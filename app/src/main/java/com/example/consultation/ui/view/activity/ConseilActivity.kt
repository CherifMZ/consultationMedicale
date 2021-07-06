package com.example.consultation.ui.view.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.work.*
import com.example.consultation.R
import com.example.consultation.constant.sharedPrefFile
import com.example.consultation.data.data.api.RetrofitService
import com.example.consultation.data.data.models.Conseil
import com.example.consultation.data.data.models.RetourConseil
import com.example.consultation.roomDao.RoomService
import com.example.consultation.service.SyncService
import kotlinx.android.synthetic.main.activity_conseil.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ConseilActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conseil)

        val idMedecin= intent.getIntExtra("idMC",0)
        val sharedPref = getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        val idPatient = sharedPref.getString("patientID", "default")

        envoyer.setOnClickListener {
            val message = editTextConseil.text.toString()
            val conseil = Conseil(idPatient?.toInt()!!, idMedecin, message)
            RoomService.context = this
            RoomService.appDataBase.getConseilDao().addConseil(conseil)
            Toast.makeText(this@ConseilActivity,conseil.conseil, Toast.LENGTH_SHORT).show()
            scheduleSycn()
        }
    }
        private fun scheduleSycn() {
            val constraints = Constraints.Builder().
            setRequiredNetworkType(NetworkType.CONNECTED).
            build()
            val req= OneTimeWorkRequest.Builder (SyncService::class.java).
            setConstraints(constraints).addTag("id1").
            build()
            val workManager = WorkManager.getInstance(this)
            workManager.enqueueUniqueWork("work", ExistingWorkPolicy.REPLACE,req)

        }
}