package com.example.consultation.ui.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.consultation.R
import com.example.consultation.constant.sharedPrefFile
import com.example.consultation.data.data.api.RetrofitService
import com.example.consultation.data.data.models.RDVResponse
import com.example.consultation.ui.adapter.RdvAdapter
import kotlinx.android.synthetic.main.activity_patient_rdv.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MedecinRdvActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medecin_rdv)

        recyclerViewRdv.addItemDecoration(
            DividerItemDecoration(
            this,
            DividerItemDecoration.VERTICAL
        )
        )
        val sharedPref = getSharedPreferences(
            sharedPrefFile, MODE_PRIVATE
        )
        val id = sharedPref.getString("medecinID","defaultvalue").toString().toInt()
        getMedecinRdv(id)
    }

    private fun getMedecinRdv(id: Int) {
        val sharedPref = getSharedPreferences(
            sharedPrefFile, MODE_PRIVATE
        )
        val role = sharedPref.getString("userRole","defaultvalue").toString()

        val cal= RetrofitService.endpoint.getMedecinRdv(id)
        cal.enqueue(object : Callback<List<RDVResponse>> {
            override fun onFailure(call: Call<List<RDVResponse>>?, t: Throwable?) {
                Toast.makeText(this@MedecinRdvActivity,"Failure in get patient RDV", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<List<RDVResponse>>?, response: Response<List<RDVResponse>>?) {
                if(response!!.isSuccessful){
                    val data=response.body()
                    recyclerViewRdv.layoutManager = LinearLayoutManager(this@MedecinRdvActivity)
                    recyclerViewRdv.adapter = RdvAdapter(this@MedecinRdvActivity,data!!,"medecin")
                }
                else{
                    Toast.makeText(this@MedecinRdvActivity,"error in data recovery", Toast.LENGTH_SHORT).show()
                }
            }


        })
    }
}