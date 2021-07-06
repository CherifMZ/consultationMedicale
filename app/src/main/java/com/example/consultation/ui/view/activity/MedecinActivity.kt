package com.example.consultation.ui.view.activity

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.consultation.R
import com.example.consultation.constant.sharedPrefFile
import com.example.consultation.data.data.repositories.AddRdvRepository
import com.example.consultation.data.data.repositories.GetPatientRepository
import com.example.consultation.data.data.repositories.GetRDVRepository
import io.github.g00fy2.quickie.QRResult
import io.github.g00fy2.quickie.ScanQRCode
import kotlinx.android.synthetic.main.activity_medecin.*

//cette activité servira au medecin a scanner le code QR
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
                this.putBoolean("connected", false)
                this.apply()
            }

            val myIntent = Intent(this, AuthentificationActivity::class.java)
            myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(myIntent)
        }
    }

    fun handleResult(result: QRResult) {
        val text = when (result) {
            is QRResult.QRSuccess -> result.content.rawValue
            QRResult.QRUserCanceled -> "User canceled"
            QRResult.QRMissingPermission -> "Missing permission"
            is QRResult.QRError -> "${result.exception.javaClass.simpleName}: ${result.exception.localizedMessage}"
        }

        val getRDVRepo = GetRDVRepository.Companion
        getRDVRepo.getRDV(this, text.toInt())

        val sharedPref = this.getSharedPreferences(
            sharedPrefFile, Context.MODE_PRIVATE)

        val idP = sharedPref.getInt("getRDVPatientId", 0)
        val getP = GetPatientRepository.Companion
        getP.getP(this, idP)

        val date = sharedPref.getString("date", "dafault")
        val hour = sharedPref.getString("heure", "dafault")
        val hourEst = sharedPref.getString("heureEst", "dafault")
        val nomP = sharedPref.getString("nomP", "dafault")
        val prenomP = sharedPref.getString("prenomP", "dafault")
        val numP = sharedPref.getString("numP", "dafault")
        val groupeSP = sharedPref.getString("groupeSanguin", "dafault")

        val alertDialog: AlertDialog.Builder = AlertDialog.Builder(this)
        alertDialog.setTitle("Information du Rendez-vous")//for set Title
        alertDialog.setMessage("Nom : "+nomP+"\n"+"Prenom : "+prenomP+"\n"+"Numero : "+numP+"\n"+
                "Groupe Sanguin : "+groupeSP+"\n"+"Date du rendez-vous : "
                +date+"\n"+"Heure du rendez-vous : "
                +hour+"\n"+"Temps Estimée du rendez-vous : "
                +hourEst) //afficher les informations du rendez vous

        alertDialog.setPositiveButton("Ok") { dialog, id ->
            dialog.dismiss()
        }

        val alert = alertDialog.create()
        alert.setCanceledOnTouchOutside(false)
        alert.show()

        val positiveButton = alert.getButton(DialogInterface.BUTTON_POSITIVE)
        positiveButton.setTextColor(Color.GREEN)
    }
}