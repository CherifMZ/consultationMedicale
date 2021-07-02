package com.example.consultation.ui.view.activity.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.consultation.R
import com.example.consultation.constant.sharedPrefFile
import com.example.consultation.data.data.repositories.AuthMedecinRepository
import com.example.consultation.data.data.repositories.AuthPatientRepository
import com.example.consultation.ui.view.activity.MedecinActivity
import kotlinx.android.synthetic.main.fragment_patient_auth.*

class PatientAuthFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_patient_auth, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        start()

        buttonConnect.setOnClickListener {
            val numero = editTextNumber.text.toString()
            val motdepasse = editTextTextPassword.text.toString()

            val auth = AuthPatientRepository.Companion
            auth.authPatient(requireContext(), numero, motdepasse)
        }
    }

    fun start() {
        val sharedPref = requireContext().getSharedPreferences(
            sharedPrefFile, Context.MODE_PRIVATE
        )
        val con = sharedPref?.getBoolean("connected", false)

        if(con == true){
            val intent= Intent(requireContext(), MedecinActivity::class.java)
            startActivity(intent)
            (context as Activity).finish()
        }
    }
}