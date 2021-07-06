package com.example.consultation.ui.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.consultation.R
import kotlinx.android.synthetic.main.activity_profil_treatment.*

class ProfilTreatmentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profil_treatment)

        val etat = intent.getSerializableExtra("etatT") as String
        val nom =intent.getSerializableExtra("nomT") as String
        val duree =intent.getSerializableExtra("dureeT") as Int
        val prise =intent.getSerializableExtra("priseT") as Int

        textViewNomT.text = "Traitement : "+nom
        textViewDurreT.text = "Durée : "+duree+" jours."
        textViewPriseT.text = "Nombre de prise : "+prise+" fois."
        textViewEtatT.text = "Etat : "+etat
    }
}