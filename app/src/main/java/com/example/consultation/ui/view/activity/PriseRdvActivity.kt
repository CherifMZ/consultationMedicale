package com.example.consultation.ui.view.activity

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.consultation.R
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.journeyapps.barcodescanner.BarcodeEncoder
import kotlinx.android.synthetic.main.activity_prise_rdv.*
import ui.mainong.pico.Pico
import ui.mainong.pico.codec.Type
import java.util.*


class PriseRdvActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prise_rdv)

        var pico = Pico(this, null, Type.TIME)
        pico.setPicoListener { calendar -> // Getting your time in 24 hour format
            val hour = calendar[Calendar.HOUR_OF_DAY]
            val min = calendar[Calendar.MINUTE]
            val sec = calendar[Calendar.SECOND]

            heureChoisie.append("Heure choisie : "+Pico.formatTime(calendar)+"\n")
        }
        pico.show()

        pico = Pico(this, null, Type.CALENDAR)
        pico.setPicoListener { calendar -> // Getting your date
            val year = calendar[Calendar.YEAR]
            val month = calendar[Calendar.MONTH]
            val day = calendar[Calendar.DAY_OF_MONTH]

            heureChoisie.append("Date choisie : "+Pico.formatDate(calendar)+"\n")
        }
        pico.show()

        val multiFormatWriter = MultiFormatWriter()

        buttonRDV.setOnClickListener {
            try {
                val bitMatrix = multiFormatWriter.encode(
                    "1",
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

/*
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".ui.view.activity.PriseRdvActivity">


    <EditText
        android:id="@+id/editTextTextPersonName"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="24dp"
        android:ems="10"
        android:inputType="textPersonName"
        android:text="Name"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintHorizontal_bias="0.497"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <Button
        android:id="@+id/button2"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="32dp"
        android:text="Button"
        app:layout_constraintEnd_toEndOf="@+id/editTextTextPersonName"
        app:layout_constraintHorizontal_bias="0.504"
        app:layout_constraintStart_toStartOf="@+id/editTextTextPersonName"
        app:layout_constraintTop_toBottomOf="@+id/editTextTextPersonName" />

    <ImageView
        android:id="@+id/imageview"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center"
        android:layout_marginTop="32dp"
        app:layout_constraintEnd_toEndOf="@+id/button2"
        app:layout_constraintStart_toStartOf="@+id/button2"
        app:layout_constraintTop_toBottomOf="@+id/button2" />

</androidx.constraintlayout.widget.ConstraintLayout>
 */