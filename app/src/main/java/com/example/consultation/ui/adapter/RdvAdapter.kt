package com.example.consultation.ui.adapter

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.consultation.R
import com.example.consultation.data.data.api.RetrofitService
import com.example.consultation.data.data.models.Medecin
import com.example.consultation.data.data.models.Patient
import com.example.consultation.data.data.models.RDVResponse
import kotlinx.android.synthetic.main.activity_patient_rdv.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RdvAdapter(val context: Context, var rdv: List<RDVResponse>,var role:String?): RecyclerView.Adapter<RdvViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RdvViewHolder {
        if(role=="medecin"){
            return RdvViewHolder(LayoutInflater.from(context).inflate(R.layout.rdv_medecin_layout, parent, false))
        }else{
            return RdvViewHolder(LayoutInflater.from(context).inflate(R.layout.rdv_patient_layout, parent, false))
        }
    }

    override fun getItemCount() = rdv.size

    @SuppressLint("RestrictedApi")
    override fun onBindViewHolder(holder: RdvViewHolder, position: Int) {

        var idMedecin=rdv[position].idMedecin
        var idPatient=rdv[position].idPatient
        if(role=="medecin"){
            getPatientById(holder,idPatient)
        }else {
            getMedecinById(holder,idMedecin)
        }
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


    private fun getPatientById(holder: RdvViewHolder,id:Int){

        val cal= RetrofitService.endpoint.getPatient(id)
        cal.enqueue(object : Callback<Patient> {
            override fun onFailure(call: Call<Patient>?, t: Throwable?) {
                Toast.makeText(context,"Failure in get patient RDV", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<Patient>?, response: Response<Patient>?) {
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


