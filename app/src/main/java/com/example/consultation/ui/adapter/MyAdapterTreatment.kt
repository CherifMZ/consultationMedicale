package com.example.consultation.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.consultation.R
import com.example.consultation.data.data.models.TreatmentResponse
import com.example.consultation.ui.view.activity.ProfilTreatmentActivity

class MyAdapterTreatment(val context: Context, var data: List<TreatmentResponse>): RecyclerView.Adapter<MyViewHolderTreatment>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolderTreatment {
        return MyViewHolderTreatment(LayoutInflater.from(context).inflate(R.layout.treatment_layout, parent, false))
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: MyViewHolderTreatment, position: Int) {
        holder.nom.text = "Traitement : "+data[position].nom
        holder.duree.text = "Durée : "+data[position].duree+" jours."
        holder.prise.text = "Nombre de prise : "+data[position].prise+" fois."
        holder.etat.text = "Etat : "+data[position].etat

        holder.itemView.setOnClickListener(){
            val intent = Intent(context, ProfilTreatmentActivity::class.java)
            intent.putExtra("nomT", data[position].nom)
            intent.putExtra("dureeT",data[position].duree)
            intent.putExtra("priseT",data[position].prise)
            intent.putExtra("etatT",data[position].etat)

            context.startActivity(intent)
        }
    }
}
open class MyViewHolderTreatment(view: View) : RecyclerView.ViewHolder(view) {
    val nom = view.findViewById<TextView>(R.id.textViewNom) as TextView
    val duree = view.findViewById<TextView>(R.id.textViewDuree) as TextView
    var prise = view.findViewById<TextView>(R.id.textViewPrise) as TextView
    val etat = view.findViewById<TextView>(R.id.textViewEtat) as TextView

}


