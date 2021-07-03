package com.example.consultation.ui.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.consultation.R
import io.github.g00fy2.quickie.QRResult
import io.github.g00fy2.quickie.ScanQRCode
import kotlinx.android.synthetic.main.activity_medecin.*

//cette activité servira au medecin pour scanner le code QR
class MedecinActivity : AppCompatActivity() {

    val scanQrCode = registerForActivityResult(ScanQRCode(), ::handleResult)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medecin)

        button.setOnClickListener { scanQrCode.launch(null) }
    }

    fun handleResult(result: QRResult) {

    }
}