package com.example.firestore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(
                this@SplashScreenActivity,LoginActivity::class.java
            )
            startActivity(intent)
            finishAffinity()
        },2500)
        val controller = WindowInsetsControllerCompat(window,window.decorView)
        controller.hide(WindowInsetsCompat.Type.statusBars())
    }
}