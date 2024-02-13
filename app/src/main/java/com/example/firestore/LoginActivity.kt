package com.example.firestore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.firestore.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    lateinit var binding:ActivityLoginBinding
    lateinit var firebaAuth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.signupText.setOnClickListener {
            startActivity(Intent(
                this@LoginActivity,SignupActivity::class.java
            ))
            finishAffinity()
        }
        firebaAuth = FirebaseAuth.getInstance()
        binding.loginBtn.setOnClickListener {
            login()
        }
        binding.forgetPassword.setOnClickListener {
            startActivity(Intent(this@LoginActivity,ResetPasswordActivity::class.java))
        }
    }

    private fun login() {
        val lEmail = binding.lEmailEt.text.toString().trim()
        val lPassword = binding.lPasswordEt.text.toString().trim()
        loginUser(lEmail,lPassword)
    }

    private fun loginUser(lEmail: String, lPassword: String) {
        if (lEmail.isNotEmpty() && lPassword.isNotEmpty()){
            if (lPassword.length>=8){
                loginWith(lEmail,lPassword)
            }
            else{
                Toast.makeText(this@LoginActivity, "Password must be at least 8 characters.", Toast.LENGTH_SHORT).show()
            }
        }else{
            Toast.makeText(this@LoginActivity, "Please fill all the fields.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loginWith(lEmail: String, lPassword: String) {
        firebaAuth.signInWithEmailAndPassword(lEmail,lPassword)
            .addOnCompleteListener {
                if (it.isSuccessful){
                    val verification = firebaAuth.currentUser?.isEmailVerified
                    if (verification == true){
                        Toast.makeText(this@LoginActivity, "Login successful.", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@LoginActivity,MainActivity::class.java))
                        finishAffinity()
                    }
                    else{
                        Toast.makeText(this@LoginActivity, "Please verify your E-mail !", Toast.LENGTH_SHORT).show()
                    }
                    binding.lEmailEt.text = null
                    binding.lPasswordEt.text = null
                }
                else{
                    Toast.makeText(this@LoginActivity, "Login failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    override fun onStart() {
        super.onStart()
        if (firebaAuth.currentUser!=null){
            startActivity(Intent(this@LoginActivity,MainActivity::class.java))
            finishAffinity()
        }
    }

}