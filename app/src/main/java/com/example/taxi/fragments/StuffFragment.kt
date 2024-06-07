package com.example.taxi.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

import com.example.taxi.R


class StuffFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_stuff, container, false)
        val phoneNumberTextView = view.findViewById<TextView>(R.id.phoneNumberTextView)

        phoneNumberTextView.setOnClickListener {
            val phoneNumber = "103"
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:$phoneNumber")
            }
            startActivity(intent)
        }
        val  policeNumberTextView = view.findViewById<TextView>(R.id.policeNumberTextView)
        policeNumberTextView.setOnClickListener {
            val phoneNumber = "102"
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:$phoneNumber")
            }
            startActivity(intent)
        }

        return view
    }



}