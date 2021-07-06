package com.example.consultation.ui.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.consultation.R
import kotlinx.android.synthetic.main.activity_accueil.*

class AccueilActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accueil)
        listeMedecins.setOnClickListener(){
            val intent = Intent(this,AffichageMedecinActivity::class.java)
            startActivity(intent)
        }

        listeRDV.setOnClickListener(){
            val intent = Intent(this,PatientRdvActivity::class.java)
            startActivity(intent)

        }
    }
}