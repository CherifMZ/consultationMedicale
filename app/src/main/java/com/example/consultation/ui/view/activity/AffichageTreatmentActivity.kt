package com.example.consultation.ui.view.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.consultation.R
import com.example.consultation.constant.sharedPrefFile
import com.example.consultation.data.data.api.RetrofitService
import com.example.consultation.data.data.models.TreatmentBody
import com.example.consultation.data.data.models.TreatmentResponse
import com.example.consultation.ui.adapter.MyAdapterTreatment
import kotlinx.android.synthetic.main.activity_affichage_medecin.*
import kotlinx.android.synthetic.main.activity_affichage_treatment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AffichageTreatmentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_affichage_treatment)
        recyclerView2.addItemDecoration(DividerItemDecoration(this,
            DividerItemDecoration.VERTICAL))

        val sharedPref = getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        val idP = sharedPref.getString("patientID", "default")
        //val idM= intent.getIntExtra("idMT", 0)
        getTreatments(idP?.toInt()!!)
    }

    private fun getTreatments(idP : Int) {
        //val tr = TreatmentBody(idP,idM)
        val call= RetrofitService.endpoint.getTreatment(idP)
        call.enqueue(object : Callback<List<TreatmentResponse>> {
            override fun onFailure(call: Call<List<TreatmentResponse>>, t: Throwable) {
                Toast.makeText(this@AffichageTreatmentActivity,"Failure in get treatment", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<List<TreatmentResponse>>, response: Response<List<TreatmentResponse>>) {
                if(response.isSuccessful){
                    val data=response.body()
                    recyclerView2.layoutManager = LinearLayoutManager(this@AffichageTreatmentActivity)
                    recyclerView2.adapter = MyAdapterTreatment(this@AffichageTreatmentActivity,data!!)
                }
                else{
                    Toast.makeText(this@AffichageTreatmentActivity,"error in data recovery", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}