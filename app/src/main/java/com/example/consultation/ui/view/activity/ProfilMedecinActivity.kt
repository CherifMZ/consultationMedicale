package com.example.consultation.ui.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.consultation.R
import com.example.consultation.constant.url
import kotlinx.android.synthetic.main.activity_profil_medecin.*

class ProfilMedecinActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profil_medecin)

        val photo = intent.getSerializableExtra("photo") as String
        val nom =intent.getSerializableExtra("nom") as String
        val prenom =intent.getSerializableExtra("prenom") as String
        val numero =intent.getSerializableExtra("numero") as String
        val specialite =intent.getSerializableExtra("specialite") as String
        val experience =intent.getSerializableExtra("experience") as Int
        val idMedecin=intent.getSerializableExtra("idMedecin") as Int

        textViewNom2.text="Dr. "+nom
        textViewPrenom2.text=prenom
        textViewNumero.text=numero
        textViewSpecialite.text=specialite
        textViewExp.text=experience.toString()

        if(photo!=null){
            Glide.with(this).load(url+ "/images/"+photo).into(doctorPhoto)
        }

        rendezVous.setOnClickListener(){
            val intent = Intent(this,MedecinRdvActivity::class.java)
            startActivity(intent)
        }



    }
}