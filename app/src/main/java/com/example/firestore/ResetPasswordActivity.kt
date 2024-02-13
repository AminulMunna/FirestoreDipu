package com.example.firestore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.firestore.databinding.ActivityResetPasswordBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ResetPasswordActivity : AppCompatActivity() {
    private lateinit var binding:ActivityResetPasswordBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        binding.resetPassBtn.setOnClickListener {
            val email = binding.emailEt.text.toString().trim()
            firebaseAuth.sendPasswordResetEmail(email).addOnSuccessListener {
                Toast.makeText(this@ResetPasswordActivity, "Please check your E-mail.", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@ResetPasswordActivity,LoginActivity::class.java))
                finishAffinity()
            }
                .addOnFailureListener {
                    Toast.makeText(this@ResetPasswordActivity, it.localizedMessage, Toast.LENGTH_SHORT).show()
                }
        }






910

    }
}