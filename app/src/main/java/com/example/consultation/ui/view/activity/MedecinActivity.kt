package com.example.consultation.ui.view.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.consultation.R
import com.example.consultation.constant.sharedPrefFile
import io.github.g00fy2.quickie.QRResult
import io.github.g00fy2.quickie.ScanQRCode
import kotlinx.android.synthetic.main.activity_medecin.*

//cette activité servira au medecin pour scanner le code QR
class MedecinActivity : AppCompatActivity() {

    val scanQrCode = registerForActivityResult(ScanQRCode(), ::handleResult)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medecin)

        //Scanner le code QR
        buttonScan.setOnClickListener {
            scanQrCode.launch(null)
        }

        //Se déconnecter de l'application
        buttonDisconnect.setOnClickListener {
            val sharedPref = getSharedPreferences(
                    sharedPrefFile, Context.MODE_PRIVATE
            )
            with(sharedPref.edit()) {
                this.putBoolean("connected",false)
                this.apply()
            }

            val myIntent = Intent(this, AuthentificationActivity::class.java)
            startActivity(myIntent)
            finish()
        }
    }

    fun handleResult(result: QRResult) {

    }
}