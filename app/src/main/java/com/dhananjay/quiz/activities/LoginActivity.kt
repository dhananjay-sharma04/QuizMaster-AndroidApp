package com.dhananjay.quiz.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.dhananjay.quiz.R
import com.dhananjay.quiz.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()
        binding.btnLogin.setOnClickListener {
            login()
        }
        binding.btnSignUp.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun login(){
        val email = binding.etEmailAddressLogin.text.toString()
        val password = binding.etPasswordLogin.text.toString()

        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

        if (email.isBlank() || password.isBlank()){
            Toast.makeText(this, "Please fill all the field", Toast.LENGTH_SHORT).show()
            return
        } else if(!email.trim().matches(emailPattern.toRegex())) {
            Toast.makeText(this, "Invalid email address", Toast.LENGTH_SHORT).show()
            return
        } else if(password.length < 6) {
            Toast.makeText(this, "password length should be 6", Toast.LENGTH_SHORT).show()
            return
        } else {
            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this){
                if (it.isSuccessful){
                    Toast.makeText(this, "Login Successfully", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Incorrect Login Credential", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}