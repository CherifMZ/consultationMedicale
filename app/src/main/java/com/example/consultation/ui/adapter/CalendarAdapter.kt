package com.example.consultation.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.consultation.R
import com.example.consultation.constant.url
import com.example.consultation.data.data.models.HeureTravailResponse
import com.example.consultation.data.data.models.Medecin
import com.example.consultation.ui.view.activity.ProfilMedecinActivity
import java.util.*


class CalendarAdapter(val context: Context, var data: List<HeureTravailResponse>): RecyclerView.Adapter<CalendarViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        return CalendarViewHolder(LayoutInflater.from(context).inflate(R.layout.heure_travail_layout, parent, false))
    }

    override fun getItemCount() = data.size

    @SuppressLint("RestrictedApi")
    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        holder.date.text = data[position].jour
        holder.Hdebut.text = "De: "+data[position].heureDebut
        holder.Hfin.text = "jusqu'a:"+data[position].heureFin
}
}

open class CalendarViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val date = view.findViewById<TextView>(R.id.date) as TextView
    val Hdebut = view.findViewById<TextView>(R.id.debut) as TextView
    var Hfin = view.findViewById<TextView>(R.id.fin) as TextView

}
