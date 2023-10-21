package com.oscarruina.pokemon.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.oscarruina.pokemon.R
import com.oscarruina.pokemon.adapter.PokemonAdapter
import com.oscarruina.pokemon.configurations.RetrofitClient
import com.oscarruina.pokemon.constants.Constants
import com.oscarruina.pokemon.dto.PokemonListDTO
import com.oscarruina.pokemon.endpoints.PokeApiService
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var rvPokemonList: RecyclerView
    private lateinit var pokemonAdapter: PokemonAdapter
    private lateinit var btnMorePokemon: Button

    private var nextPageUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = resources.getString(R.string.app_name)

        sharedPreferences = getSharedPreferences(Constants.SP_CREDENTIALS, Context.MODE_PRIVATE)

        rvPokemonList = findViewById(R.id.rvPokemonList)
        pokemonAdapter = PokemonAdapter(mutableListOf(), this)
        rvPokemonList.adapter = pokemonAdapter

        //Obtener los datos iniciales
        loadInitialPokemonData()

        //Cargar mas datos
        btnMorePokemon = findViewById(R.id.btnMorePokemon)
        btnMorePokemon.setOnClickListener {
            loadMorePokemonData()
        }

    }

    private fun loadInitialPokemonData() {
        val api = RetrofitClient.retrofit.create(PokeApiService::class.java)
        val callGetPokemon = api.getAllPokemon()

        callGetPokemon.enqueue(object : retrofit2.Callback<PokemonListDTO>{
            override fun onResponse(
                call: Call<PokemonListDTO>,
                response: Response<PokemonListDTO>
            ) {

                val pokemonList = response.body()?.results

                if (pokemonList != null){
                    nextPageUrl = response.body()?.next
                    pokemonAdapter.updateData(pokemonList)
                }
            }

            override fun onFailure(call: Call<PokemonListDTO>, t: Throwable) {
                Log.e("Error", t.message ?:" ")
            }
        })
    }

    private fun loadMorePokemonData() {
        nextPageUrl?.let { url ->
            val api = RetrofitClient.retrofit.create(PokeApiService::class.java)
            val callGetPokemon = api.getPokemonByUrl(url)

            callGetPokemon.enqueue(object : retrofit2.Callback<PokemonListDTO> {
                override fun onResponse(
                    call: Call<PokemonListDTO>,
                    response: Response<PokemonListDTO>
                ) {
                    val additionalPokemonList = response.body()?.results

                    if (additionalPokemonList != null) {
                        nextPageUrl = response.body()?.next
                        pokemonAdapter.addData(additionalPokemonList)
                    }
                }

                override fun onFailure(call: Call<PokemonListDTO>, t: Throwable) {
                    Log.e("Error", t.message ?: " ")
                }
            })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
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
        return super.onOptionsItemSelected(item)
    }
}