package com.example.taxi.authoriztion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.lifecycleScope

import com.example.taxi.R

import com.example.taxi.utils.Navigation_menu
import com.example.taxi.utils.UserMethods
import kotlinx.coroutines.launch


class RegisterScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.reg)
    }

    fun regClick(view: View) {

        val email : String = findViewById<EditText?>(R.id.reg_email).text.toString();
        val pass2 : String = findViewById<EditText?>(R.id.reg_password2).text.toString();
        val pass : String = findViewById<EditText?>(R.id.pass_edit).text.toString();

        val emailPattern : Regex = Regex("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")
        if(email == "" || pass2 == "" || pass == "" || !email.matches(emailPattern) || pass.length < 6 || pass != pass2){
            Toast.makeText(this, "Проверьте правильность введенных данных!", Toast.LENGTH_SHORT).show()
            return
        }
        val toast: Toast = Toast.makeText(this, "1", Toast.LENGTH_SHORT)
        val code = Intent(this, Navigation_menu::class.java)
        try {
            lifecycleScope.launch {
                if(!UserMethods().SignUp(email, pass)) {
                    toast.setText("Данный E-Mail уже зарегестрирован")
                    toast.show()
                    return@launch
                }
                val user = UserMethods().getUserSession()
                if (user == null) {
                    toast.setText("Произошла ошибка")
                    toast.show()
                    return@launch
                }
                val sPref = getSharedPreferences("login", MODE_PRIVATE).edit()
                UserMethods().saveUser(sPref, email, pass)
                toast.setText("Зареган")
                toast.show()
                startActivity(code)
            }
        }catch (_: Exception){
            toast.setText("Произошла ошибка")
            toast.show()
        }
    }
    fun authClick(view: View) {
        val int = Intent(this, LoginScreen::class.java)
        startActivity(int)
        finish()
    }
}