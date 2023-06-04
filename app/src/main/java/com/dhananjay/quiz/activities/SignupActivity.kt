package com.dhananjay.quiz.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.dhananjay.quiz.R
import com.dhananjay.quiz.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth

class SignupActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()

        binding.btnSignUp.setOnClickListener {
            signUpUser()
        }

        binding.btnLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun signUpUser(){

        val email = binding.etEmailAddress.text.toString()
        val password = binding.etPassword.text.toString()
        val cnfPassword = binding.etCnfPassword.text.toString()

        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

        if (email.isBlank() || password.isBlank() || cnfPassword.isBlank()) {
            Toast.makeText(this, "Please fill all the field", Toast.LENGTH_SHORT).show()
            return
        } else if(!email.trim().matches(emailPattern.toRegex())) {
            Toast.makeText(this, "Invalid email address", Toast.LENGTH_SHORT).show()
            return
        } else if(password.length < 6) {
            Toast.makeText(this, "password length should be 6", Toast.LENGTH_SHORT).show()
            return
        } else if(password != cnfPassword) {
            Toast.makeText(this, "Password and Confirm Password are different", Toast.LENGTH_SHORT).show()
            return
        } else {
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) {
                    if (it.isSuccessful) {
                        Toast.makeText(this, "Signup successfully!", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, "Error creating user!", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}
