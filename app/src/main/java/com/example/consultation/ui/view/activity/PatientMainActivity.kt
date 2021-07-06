package com.example.consultation.ui.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.consultation.R
import kotlinx.android.synthetic.main.activity_patient_main.*

class PatientMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_main)

        textViewListMed.setOnClickListener {
            val myIntent = Intent(this, AffichageMedecinActivity::class.java)
            startActivity(myIntent)
        }

        textViewListRDV.setOnClickListener {
            val intent = Intent(this,PatientRdvActivity::class.java)
            startActivity(intent)
        }

        textViewListTreat.setOnClickListener {
            val myIntent = Intent(this, AffichageTreatmentActivity::class.java)
            startActivity(myIntent)
        }
    }
}