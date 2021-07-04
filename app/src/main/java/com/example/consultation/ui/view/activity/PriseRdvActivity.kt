package com.example.consultation.ui.view.activity

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.consultation.R
import com.example.consultation.constant.sharedPrefFile
import com.example.consultation.data.data.repositories.AddRdvRepository
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.journeyapps.barcodescanner.BarcodeEncoder
import kotlinx.android.synthetic.main.activity_prise_rdv.*
import ui.mainong.pico.Pico
import ui.mainong.pico.codec.Type
import java.util.*


class PriseRdvActivity : AppCompatActivity() {
    private var time : String =""
    private var date : String =""
    private var heureEstimee : String =""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prise_rdv)

        var pico = Pico(this, null, Type.TIME)
        pico.setPicoListener { calendar -> // Getting your time in 24 hour format
            val hour = calendar[Calendar.HOUR_OF_DAY]
            val min = calendar[Calendar.MINUTE]
            val sec = calendar[Calendar.SECOND]

            time = hour.toString()+":"+min.toShort()+":"+sec.toString()

            var minEstime = min+15
            if (minEstime==60) {
                val hourEstime = hour+1
                minEstime =0
                heureEstimee = hourEstime.toString()+":"+minEstime.toShort()+":"+sec.toString()
            } else {
                heureEstimee = hour.toString()+":"+minEstime.toShort()+":"+sec.toString()
            }

            heureChoisie.append("Heure choisie : "+Pico.formatTime(calendar)+"\n")
        }
        pico.show()

        pico = Pico(this, null, Type.CALENDAR)
        pico.setPicoListener { calendar -> // Getting your date
            val year = calendar[Calendar.YEAR]
            val month = calendar[Calendar.MONTH]
            val day = calendar[Calendar.DAY_OF_MONTH]

            date = year.toString()+"-"+month.toShort()+"-"+day.toString()
            heureChoisie.append("Date choisie : "+Pico.formatDate(calendar)+"\n")
        }
        pico.show()

        val multiFormatWriter = MultiFormatWriter()

        buttonRDV.setOnClickListener {

            val sharedPref = getSharedPreferences(
                sharedPrefFile, Context.MODE_PRIVATE
            )

            val add = AddRdvRepository.Companion
            val idP = sharedPref.getString("patientID", "default")
            add.addRDV(this, 1, idP?.toInt()!!,
                date, time, heureEstimee)

            sharedPref.getInt("idRDV", 0)
            try {
                val bitMatrix = multiFormatWriter.encode(
                    sharedPref.getInt("idRDV", 0).toString(),
                    BarcodeFormat.QR_CODE,
                    500,
                    500
                )
                val barcodeEncoder = BarcodeEncoder()
                val bitmap = barcodeEncoder.createBitmap(bitMatrix)
                imageview.setImageBitmap(bitmap)
                val uri = saveImage(bitmap, "RendezVous")
                toast("saved : $uri")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        buttonDisconnect2.setOnClickListener {
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

    fun Context.toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun saveImage(bitmap: Bitmap, title: String):Uri{
        // Save image to gallery
        val savedImageURL = MediaStore.Images.Media.insertImage(
            contentResolver,
            bitmap,
            title,
            "Image of $title"
        )
        // Parse the gallery image url to uri
        return Uri.parse(savedImageURL)
    }
}