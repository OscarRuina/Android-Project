package com.oscarruina.pokemon.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.oscarruina.pokemon.R

class TermsAndConditionsActivity : AppCompatActivity() {

    private lateinit var btnTermsAndConditions: Button
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_terms_and_conditions)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = resources.getString(R.string.app_name)

        btnTermsAndConditions = findViewById(R.id.btnTerminosYCondiciones)

        btnTermsAndConditions.setOnClickListener {
            val registerUser = Intent(this, RegisterUserActivity::class.java)
            startActivity(registerUser)
            finish()
        }
    }
}