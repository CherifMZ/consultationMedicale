package com.example.consultation.ui.view.activity.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.consultation.R
import com.example.consultation.constant.sharedPrefFile
import com.example.consultation.ui.view.activity.MedecinActivity
import kotlinx.android.synthetic.main.fragment_authentification.*

class AuthentificationFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_authentification, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        start()

        buttonPatient.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_authentificationFragment_to_patientAuthFragment)
        }

        buttonMedecin.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_authentificationFragment_to_medecinAuthFragment)
        }
    }

    fun start() {
        val sharedPref = requireContext().getSharedPreferences(
                sharedPrefFile, Context.MODE_PRIVATE
        )

        if((sharedPref?.getBoolean("connected", false) == true)
                && (sharedPref?.getString("role", "default").equals("medecin"))){
            val intent= Intent(requireContext(), MedecinActivity::class.java)
            startActivity(intent)
            (context as Activity).finish()
        } else if ((sharedPref?.getBoolean("connected", false) == true)
                && (sharedPref?.getString("role", "default").equals("patient"))) {
            val intent= Intent(requireContext(), MedecinActivity::class.java)
            startActivity(intent)
            (context as Activity).finish()
        }
    }
}