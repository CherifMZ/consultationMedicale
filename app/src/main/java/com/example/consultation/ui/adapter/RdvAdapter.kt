package com.example.consultation.ui.adapter

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.consultation.R
import com.example.consultation.constant.url
import com.example.consultation.data.data.api.RetrofitService
import com.example.consultation.data.data.models.Medecin
import com.example.consultation.data.data.models.RDVBody
import com.example.consultation.data.data.models.RDVResponse
import kotlinx.android.synthetic.main.activity_patient_rdv.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RdvAdapter(val context: Context, var rdv: List<RDVResponse>): RecyclerView.Adapter<RdvViewHolder>() {
    var nom:String = "";
    var prenom:String=""
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RdvViewHolder {
        return RdvViewHolder(LayoutInflater.from(context).inflate(R.layout.rdv_layout, parent, false))
    }

    override fun getItemCount() = rdv.size

    @SuppressLint("RestrictedApi")
    override fun onBindViewHolder(holder: RdvViewHolder, position: Int) {

        var id=rdv[position].idMedecin
        //val data =withContext(Dispatchers.IO) {}

        getMedecinById(holder,id)
        holder.heure.text = rdv[position].heureRdv
        holder.date.text = rdv[position].dateRdv


        //Glide.with(context).load(url+ "images/" +medecin.photo).into(holder.image)
    }




    private fun getMedecinById(holder: RdvViewHolder,id:Int){

            val cal= RetrofitService.endpoint.getMedecin(id)
            cal.enqueue(object : Callback<Medecin> {
                override fun onFailure(call: Call<Medecin>?, t: Throwable?) {
                    Toast.makeText(context,"Failure in get patient RDV", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<Medecin>?, response: Response<Medecin>?) {
                    if(response!!.isSuccessful){
                        val data=response.body()
                        if(data!=null){

                            holder.nom.text=data.nom
                            holder.prenom.text =data.prenom
                            Log.d(TAG, "Data successfully downloaded");
                        }
                    }
                    else{
                        Toast.makeText(context,"error in data recovery", Toast.LENGTH_SHORT).show()
                    }
                }
            })

    }




}

open class RdvViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val nom = view.findViewById<TextView>(R.id.nomMedecin) as TextView
    val prenom = view.findViewById<TextView>(R.id.prenomMedecin) as TextView
    //val image = view.findViewById<ImageView>(R.id.imageView2) as ImageView
    val heure=view.findViewById<ImageView>(R.id.heure) as TextView
    val date=view.findViewById<ImageView>(R.id.date) as TextView

}


