package com.oscarruina.pokemon.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.oscarruina.pokemon.R
import com.oscarruina.pokemon.configurations.RetrofitClient
import com.oscarruina.pokemon.constants.Constants
import com.oscarruina.pokemon.dto.PokemonDetailsDTO
import com.oscarruina.pokemon.endpoints.PokeApiService
import retrofit2.Call
import retrofit2.Response

class PokemonDetailsActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var sharedPreferences: SharedPreferences

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_details)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = resources.getString(R.string.app_name)

        sharedPreferences = getSharedPreferences(Constants.SP_CREDENTIALS, Context.MODE_PRIVATE)


        val bundle : Bundle? = intent.extras
        val api = RetrofitClient.retrofit.create(PokeApiService::class.java)

        if (bundle != null){
            val pokemonName = bundle.getString(Constants.POKEMON_NAME)
            val callGetPokemonByName = pokemonName?.let { api.getPokemonByName(it) }

            callGetPokemonByName?.enqueue(object : retrofit2.Callback<PokemonDetailsDTO>{
                @SuppressLint("SetTextI18n")
                override fun onResponse(
                    call: Call<PokemonDetailsDTO>,
                    response: Response<PokemonDetailsDTO>
                ) {
                    val pokemonDetails = response.body()

                    if (pokemonDetails != null){
                        val imageView = findViewById<ImageView>(R.id.ivPokemonImage)
                        imageView.maxHeight = 200
                        imageView.maxWidth = 200
                        val imageUrl = pokemonDetails.sprites.other.home.front_default
                        if (!imageUrl.isNullOrBlank()) {
                            Glide.with(this@PokemonDetailsActivity)
                                .load(imageUrl)
                                .into(imageView)
                        }

                        val nameTextView = findViewById<TextView>(R.id.tvPokemonName)
                        val idTextView = findViewById<TextView>(R.id.tvPokemonId)
                        val statsTextView = findViewById<TextView>(R.id.tvPokemonStats)
                        val typesTextView = findViewById<TextView>(R.id.tvPokemonTypes)
                        val heightTextView = findViewById<TextView>(R.id.tvPokemonHeight)
                        val weightTextView = findViewById<TextView>(R.id.tvPokemonWeight)

                        idTextView.text = "Pokedex: ${pokemonDetails.id}"
                        nameTextView.text = "Nombre: ${pokemonDetails.name}"

                        val typesList = pokemonDetails.types
                        val typeNames = typesList.map { it.type.name }
                        val typesString = typeNames.joinToString(", ")
                        typesTextView.text = "Tipo: $typesString"

                        val height = String.format("%.1f", pokemonDetails.height / 10.0)
                        heightTextView.text = "Altura: $height m"

                        val weight = String.format("%.1f", pokemonDetails.weight / 10.0)
                        weightTextView.text = "Peso: $weight kg"

                        val statsList = pokemonDetails.stats
                        val statNames = listOf("HP", "ATQ", "DEF", "ATQ E", "DEF E", "VEL") // Nombres de las estadísticas en el mismo orden
                        val statsString = buildString {
                            append("Estadísticas Base ")
                            statNames.forEachIndexed { index, statName ->
                                val baseStat = statsList.getOrNull(index)?.base_stat ?: 0 // Obtén la estadística base o 0 si no existe
                                append("$statName: $baseStat")
                                if (index < statNames.size - 1) {
                                    append(", ")
                                }
                            }
                        }
                        statsTextView.text = statsString
                    }

                    else{
                        Toast.makeText(this@PokemonDetailsActivity,"Pokemon not found",Toast.LENGTH_SHORT).show()
                    }

                }

                override fun onFailure(call: Call<PokemonDetailsDTO>, t: Throwable) {
                    Log.e("Error", t.message ?:" ")
                }
            })
        }





    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        menuInflater.inflate(R.menu.back_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.logOut){
            val editor = sharedPreferences.edit()
            editor.remove(Constants.USER)
            editor.remove(Constants.PASSWORD)
            editor.apply()

            val login = Intent(this, LoginActivity::class.java)
            startActivity(login)
            finish()
        }
        if (item.itemId == R.id.item_back){
            val main = Intent(this, MainActivity::class.java)
            startActivity(main)
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}