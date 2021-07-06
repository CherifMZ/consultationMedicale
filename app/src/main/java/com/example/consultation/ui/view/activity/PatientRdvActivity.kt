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
import com.example.consultation.data.data.models.Medecin
import com.example.consultation.data.data.models.RDVBody
import com.example.consultation.data.data.models.RDVResponse
import com.example.consultation.ui.adapter.MyAdapter
import com.example.consultation.ui.adapter.RdvAdapter
import kotlinx.android.synthetic.main.activity_affichage_medecin.*
import kotlinx.android.synthetic.main.activity_patient_rdv.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.sql.DriverManager.println

class PatientRdvActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_rdv)

        recyclerViewRdv.addItemDecoration(DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
        ))
        val sharedPref = getSharedPreferences(
                sharedPrefFile, MODE_PRIVATE
        )
        val id = sharedPref.getString("patientID","defaultvalue").toString().toInt()
        getPatientRdv(id)

    }



    private fun getPatientRdv(id:Int){
        val cal= RetrofitService.endpoint.getPatientRdv(id)
        cal.enqueue(object : Callback<List<RDVResponse>> {
            override fun onFailure(call: Call<List<RDVResponse>>?, t: Throwable?) {
                Toast.makeText(this@PatientRdvActivity,"Failure in get patient RDV", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<List<RDVResponse>>?, response: Response<List<RDVResponse>>?) {
                if(response!!.isSuccessful){
                    val data=response.body()
                    recyclerViewRdv.layoutManager = LinearLayoutManager(this@PatientRdvActivity)
                    recyclerViewRdv.adapter = RdvAdapter(this@PatientRdvActivity,data!!)
                }
                else{
                    Toast.makeText(this@PatientRdvActivity,"error in data recovery", Toast.LENGTH_SHORT).show()
                }
            }


        })
    }
}