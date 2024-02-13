package com.example.firestore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.firestore.databinding.ActivitySignupBinding
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SignupActivity : AppCompatActivity() {
    private val binding: ActivitySignupBinding by lazy {
        ActivitySignupBinding.inflate(layoutInflater)
    }
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.loginText.setOnClickListener {
            val intent = Intent(this@SignupActivity, LoginActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }

        firebaseAuth = FirebaseAuth.getInstance()
        binding.SignupBtn.setOnClickListener {
            signup()
        }

    }


    private fun signup() {
        val sEmail = binding.emailEt.text.toString().trim()
        val sPassword = binding.passwordEt.text.toString().trim()
        val sConfirmPassword = binding.confirmPasswordEt.text.toString().trim()
        signupUser(sEmail, sPassword, sConfirmPassword)
    }

    private fun signupUser(sEmail: String, sPassword: String, sConfirmPassword: String) {
        if (sEmail.isNotEmpty() && sPassword.isNotEmpty() && sConfirmPassword.isNotEmpty()) {
            if (sPassword == sConfirmPassword) {
                createUser(sEmail, sPassword)
            } else {
                Toast.makeText(
                    this@SignupActivity,
                    "Password and Retype Password should be same.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            Toast.makeText(this@SignupActivity, "Please fill all the fields.", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun createUser(email: String, password: String) {
        if (password.length < 8) {
            Toast.makeText(
                this@SignupActivity,
                "Password must be at least 8 characters.",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        firebaseAuth.currentUser?.sendEmailVerification()
                            ?.addOnSuccessListener {
                                firestore = FirebaseFirestore.getInstance()
                                val collection = firestore.collection("Users").document()
                                val user = User(collection.id, email, password, Timestamp.now())
                                collection.set(user)
                                Toast.makeText(
                                    this,
                                    "Please check your mail for verification.",
                                    Toast.LENGTH_SHORT
                                ).show()
                                binding.emailEt.text = null
                                binding.passwordEt.text = null
                                binding.confirmPasswordEt.text = null
                                startActivity(Intent(this@SignupActivity,LoginActivity::class.java))
                            }
                            ?.addOnFailureListener {
                                Toast.makeText(
                                    this@SignupActivity,
                                    "Invalid email address.",
                                    Toast.LENGTH_SHORT
                                ).show()

                            }

                    } else {
                        Toast.makeText(this@SignupActivity, "Signup Failed.", Toast.LENGTH_SHORT)
                            .show()
                    }


                }
        }
    }

}