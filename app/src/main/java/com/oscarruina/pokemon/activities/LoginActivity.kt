package com.oscarruina.pokemon.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import com.oscarruina.pokemon.R
import com.oscarruina.pokemon.configurations.AppDataBase
import com.oscarruina.pokemon.constants.Constants

class LoginActivity : AppCompatActivity() {

    private lateinit var etUser: EditText
    private lateinit var etPassword: EditText
    private lateinit var cbRememberUser: CheckBox
    private lateinit var btnRegister: Button
    private lateinit var btnLogin: Button
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = resources.getString(R.string.app_name)

        //Vinculamos los atributos a las vistas
        etUser = findViewById(R.id.etUsuario)
        etPassword = findViewById(R.id.etPassword)
        cbRememberUser = findViewById(R.id.cbRecordarUsuario)
        btnRegister = findViewById(R.id.btnRegistrarse)
        btnLogin = findViewById(R.id.btnLogin)

        //Configuro las shared preferences
        sharedPreferences = getSharedPreferences(Constants.SP_CREDENTIALS, Context.MODE_PRIVATE)

        //Verifico que los datos de sesion esten guardados.
        val savedUser = sharedPreferences.getString(Constants.USER,null)
        val savedPassword = sharedPreferences.getString(Constants.PASSWORD,null)

        if (savedUser != null && savedPassword != null){
            //Los datos estan guardados, inicio la actividad.
            Log.i("Valor del SharedPreferences", savedUser)
            startMainActivity(savedUser)
        }

        btnRegister.setOnClickListener {
            val termsAndConditions = Intent(this, TermsAndConditionsActivity::class.java)
            startActivity(termsAndConditions)
            finish()
        }

        btnLogin.setOnClickListener {
            val username = etUser.text.toString()
            val password = etPassword.text.toString()
            if (username.isEmpty() || password.isEmpty()){
                Toast.makeText(this, "Completar Datos", Toast.LENGTH_SHORT).show()
            }else{
                login(username,password)
            }
        }
    }

    private fun login(username: String?, password: String?) {
        if (cbRememberUser.isChecked){
            val editor = sharedPreferences.edit()
            editor.putString(Constants.USER,username)
            editor.putString(Constants.PASSWORD,password)
            editor.apply()
        }
        if (username != null) {
            val bd = AppDataBase.getDatabase(this)
            val user = bd.UserDao().getUserByUsername(username)
            if (user == null){
                Toast.makeText(this, "Usuario no Registrado", Toast.LENGTH_SHORT).show()
            }
            else if (user.password != password){
                Toast.makeText(this, "La Password es Incorrecta", Toast.LENGTH_SHORT).show()
            }
            else{
                startMainActivity(username)
            }
        }
    }

    private fun startMainActivity(savedUser: String?) {
        val main = Intent(this, MainActivity::class.java)
        main.putExtra(Constants.USER,savedUser) //Agregamos el nombre para mandarlo
        startActivity(main) //Ejecutamos el intent
        finish()
    }
}