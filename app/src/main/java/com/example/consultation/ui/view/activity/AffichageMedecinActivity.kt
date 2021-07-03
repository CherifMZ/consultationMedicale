package com.example.consultation.ui.view.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.consultation.R
import com.example.consultation.data.data.models.Medecin
import com.example.consultation.data.data.api.RetrofitService
import com.example.consultation.ui.adapter.MyAdapter
import kotlinx.android.synthetic.main.activity_affichage_medecin.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AffichageMedecinActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_affichage_medecin)
        recyclerView.addItemDecoration(DividerItemDecoration(
            this,
            DividerItemDecoration.VERTICAL
        )
        )
        getMedecins()
    }



    private fun getMedecins(){
        val cal= RetrofitService.endpoint.getAllMedecins()
        cal.enqueue(object : Callback<List<Medecin>> {
            override fun onFailure(call: Call<List<Medecin>>?, t: Throwable?) {
                Toast.makeText(this@AffichageMedecinActivity,"Failure in get medecins", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<List<Medecin>>?, response: Response<List<Medecin>>?) {
                if(response!!.isSuccessful){
                    val data=response.body()
                    recyclerView.layoutManager = LinearLayoutManager(this@AffichageMedecinActivity)
                    recyclerView.adapter = MyAdapter(this@AffichageMedecinActivity,data!!)
                }
                else{
                    Toast.makeText(this@AffichageMedecinActivity,"error in data recovery", Toast.LENGTH_SHORT).show()
                }
            }

        })
    }
}