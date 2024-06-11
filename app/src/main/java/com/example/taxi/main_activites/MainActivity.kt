package com.example.taxi.main_activites

import android.content.Intent
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.widget.Toolbar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout

import com.example.taxi.R
import com.example.taxi.authoriztion.LoginScreen
import com.example.taxi.authoriztion.RegisterScreen
import com.example.taxi.fragments.TaxiOrderFragment
import com.google.android.material.navigation.NavigationView
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.yandex.mapkit.MapKitFactory


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_screen)
    }



    fun authClick(view: View) {
        val int = Intent(this, LoginScreen::class.java)
        startActivity(int)
        finish()
    }
    fun regClick(view: View) {
        val int = Intent(this, RegisterScreen::class.java)
        startActivity(int)
        finish()
    }

}