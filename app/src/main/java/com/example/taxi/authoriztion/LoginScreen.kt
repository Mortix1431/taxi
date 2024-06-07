package com.example.taxi.authoriztion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.lifecycleScope

import com.example.taxi.R
import com.example.taxi.authoriztion.RegisterScreen
import com.example.taxi.utils.Navigation_menu
import com.example.taxi.utils.UserMethods
import kotlinx.coroutines.launch

class LoginScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.autharization)
    }

    fun authClick(view: View) {
        val mail = findViewById<EditText>(R.id.log_email).text.toString()
        val pass = findViewById<EditText>(R.id.pass_edit).text.toString()
        val tost = Toast.makeText(this, "1", Toast.LENGTH_SHORT)
        val int = Intent(this, Navigation_menu::class.java)
        lifecycleScope.launch() {
            if (!UserMethods().Auth(mail, pass)){
                tost.setText("Неправильный логин или пароль")
                tost.show()
            }else{
                val sPref = getSharedPreferences("login", MODE_PRIVATE).edit()
                UserMethods().saveUser(sPref, mail, pass)
                startActivity(int)
                finish()
            }
        }
    }
    fun regClick(view: View) {
        val intentn = Intent(this, RegisterScreen::class.java)
        startActivity(intentn)
        finish()
    }
}