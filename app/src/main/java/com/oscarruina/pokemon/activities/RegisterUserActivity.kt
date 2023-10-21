package com.oscarruina.pokemon.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.oscarruina.pokemon.R
import com.oscarruina.pokemon.configurations.AppDataBase
import com.oscarruina.pokemon.models.User

class RegisterUserActivity : AppCompatActivity() {

    private lateinit var etName: EditText
    private lateinit var etLastName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etUserName: EditText
    private lateinit var etPassword1: EditText
    private lateinit var etPassword2: EditText
    private lateinit var btnSave: Button
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_user)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = resources.getString(R.string.app_name)

        etName = findViewById(R.id.etNombre)
        etLastName = findViewById(R.id.etApellido)
        etUserName = findViewById(R.id.etUserName)
        etEmail = findViewById(R.id.etEmail)
        etPassword1 = findViewById(R.id.etPassword1)
        etPassword2 = findViewById(R.id.etPassword2)
        btnSave = findViewById(R.id.btnGuardarUsuario)

        btnSave.setOnClickListener {
            val name = etName.text.toString()
            val lastName = etLastName.text.toString()
            val username = etUserName.text.toString()
            val email = etEmail.text.toString()
            val password = etPassword1.text.toString()
            val password2 = etPassword2.text.toString()

            if (name.isEmpty() || lastName.isEmpty() || username.isEmpty() || email.isEmpty() || password.isEmpty() || password2.isEmpty()){
                Toast.makeText(this, "Faltan Datos Obligatorios", Toast.LENGTH_SHORT).show()
            }

            else if(password != password2){
                Toast.makeText(this, "La Password no Coincide", Toast.LENGTH_SHORT).show()
            }

            else{
                saveUser(name,lastName,username,email,password)
                etName.text.clear()
                etLastName.text.clear()
                etUserName.text.clear()
                etEmail.text.clear()
                etPassword1.text.clear()
                etPassword2.text.clear()
                val main = Intent(this, LoginActivity::class.java)
                startActivity(main)
                finish()
            }
        }
    }

    private fun saveUser(
        name: String,
        lastName: String,
        username: String,
        email: String,
        password: String
    ) {
        var newUser = User(name, lastName, email, username, password, true)
        AppDataBase.getDatabase(applicationContext).UserDao().insert(newUser)
    }
}