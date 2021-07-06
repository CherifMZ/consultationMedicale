package com.example.consultation.ui.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.consultation.R
import com.example.consultation.constant.sharedPrefFile
import com.example.consultation.data.data.api.RetrofitService
import com.example.consultation.data.data.models.HeureTravailResponse
import com.example.consultation.data.data.models.Medecin
import com.example.consultation.ui.adapter.CalendarAdapter
import com.example.consultation.ui.adapter.MyAdapter
import kotlinx.android.synthetic.main.activity_affichage_medecin.*
import kotlinx.android.synthetic.main.activity_calendar.*
import kotlinx.android.synthetic.main.activity_profil_medecin.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CalendarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)
        RecyclerViewCalendar.addItemDecoration(
            DividerItemDecoration(
            this,
            DividerItemDecoration.VERTICAL
        )
        )
        val id=intent.getSerializableExtra("id") as Int

        getCalendar(id)
    }

    private fun getCalendar(id: Int) {
        val cal= RetrofitService.endpoint.getMedecinCalendar(id)

        cal.enqueue(object : Callback<List<HeureTravailResponse>> {
            override fun onFailure(call: Call<List<HeureTravailResponse>>?, t: Throwable?) {
                Toast.makeText(this@CalendarActivity,"Failure in get Calendar", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<List<HeureTravailResponse>>?, response: Response<List<HeureTravailResponse>>?) {
                if(response!!.isSuccessful){
                    val data=response.body()
                    RecyclerViewCalendar.layoutManager = LinearLayoutManager(this@CalendarActivity)
                    RecyclerViewCalendar.adapter = CalendarAdapter(this@CalendarActivity,data!!)
                }
                else{
                    Toast.makeText(this@CalendarActivity,"error in data recovery", Toast.LENGTH_SHORT).show()
                }
            }

        })
    }
}