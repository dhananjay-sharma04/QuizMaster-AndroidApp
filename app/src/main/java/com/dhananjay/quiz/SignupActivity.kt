package com.dhananjay.quiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class SignupActivity : AppCompatActivity() {

    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        firebaseAuth = FirebaseAuth.getInstance()

        findViewById<Button>(R.id.btnSignUp)?.setOnClickListener(View.OnClickListener {
            signUpUser()
        })
    }

    private fun signUpUser(){

        val email = findViewById<EditText>(R.id.etEmailAddress1)?.text.toString()
        val password = findViewById<EditText>(R.id.etPassword)?.text.toString()
        val cnfPassword = findViewById<EditText>(R.id.etCnfPassword)?.text.toString()

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
                    } else {
                        Toast.makeText(this, "Error creating user!", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}
