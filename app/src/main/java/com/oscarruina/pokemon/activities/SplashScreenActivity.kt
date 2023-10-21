package com.oscarruina.pokemon.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.oscarruina.pokemon.R

class SplashScreenActivity : AppCompatActivity() {

    private val delay : Long = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val r = Runnable {
            val login = Intent(this, LoginActivity::class.java)
            startActivity(login)
            finish()
        }
        Handler(Looper.getMainLooper()).postDelayed(r, delay)

    }
}