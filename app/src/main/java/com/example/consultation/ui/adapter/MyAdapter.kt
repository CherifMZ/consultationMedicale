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
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.consultation.R
import com.example.consultation.constant.url
import com.example.consultation.ui.view.activity.ProfilMedecinActivity
import com.example.consultation.data.data.models.Medecin

class MyAdapter(val context: Context, var data: List<Medecin>): RecyclerView.Adapter<MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(context).inflate(R.layout.medecin_layout, parent, false))
    }

    override fun getItemCount() = data.size

    @SuppressLint("RestrictedApi")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.nom.text = "Dr. "+data[position].nom
        holder.prenom.text = data[position].prenom
        holder.numero.text = "tel: "+data[position].numero
        holder.specialite.text = data[position].specialite

        Glide.with(context).load(url+ "images/" +data[position].photo).into(holder.image)
        holder.numero.setOnClickListener() {

            val uri = Uri.parse("tel:" +data[position].numero)
            val intent = Intent(Intent.ACTION_DIAL, uri)
            context.startActivity(intent)
        }

        holder.map.setOnClickListener(){
            val latitude = data[position].latitude
            val longitude = data[position].longitude
            val geoLocation = Uri.parse("google.navigation:q=$latitude,$longitude")
            val intent = Intent(Intent.ACTION_VIEW,geoLocation)
            intent.setPackage("com.google.android.apps.maps");
            context.startActivity(intent)
        }

        holder.itemView.setOnClickListener(){
            val intent = Intent(context,ProfilMedecinActivity::class.java)
            intent.putExtra("photo",data[position].photo)
            intent.putExtra("nom",data[position].nom)
            intent.putExtra("prenom",data[position].prenom)
            intent.putExtra("numero",data[position].numero)
            intent.putExtra("specialite",data[position].specialite)
            intent.putExtra("experience",data[position].experience)


            context.startActivity(intent)
        }
    }
}

open class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val nom = view.findViewById<TextView>(R.id.textNom) as TextView
    val prenom = view.findViewById<TextView>(R.id.textPrenom) as TextView
    var numero = view.findViewById<TextView>(R.id.textNumero) as TextView
    val specialite = view.findViewById<TextView>(R.id.textSpecialite) as TextView
    val image = view.findViewById<ImageView>(R.id.imageView2) as ImageView
    val map=view.findViewById<ImageView>(R.id.imageView3) as ImageView

}
