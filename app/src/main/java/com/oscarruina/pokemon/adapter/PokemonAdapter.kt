package com.oscarruina.pokemon.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.oscarruina.pokemon.R
import com.oscarruina.pokemon.activities.PokemonDetailsActivity
import com.oscarruina.pokemon.configurations.RetrofitClient
import com.oscarruina.pokemon.constants.Constants
import com.oscarruina.pokemon.dto.PokemonDTO
import com.oscarruina.pokemon.dto.PokemonDetailsDTO
import com.oscarruina.pokemon.endpoints.PokeApiService
import retrofit2.Call
import retrofit2.Response

class PokemonAdapter(private val pokemonList: MutableList<PokemonDTO>, val context: Context)
    : RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.pokemon_item,parent,false)

        return PokemonViewHolder(view)
    }

    override fun getItemCount(): Int {
        return pokemonList.size
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        //Permite manipular cada contenido de la lista
        val item = pokemonList[position]
        holder.txtPokemonName.text = item.name
        holder.txtPokemonUrl.text = item.url

        // Realiza una solicitud a la URL del Pokémon para obtener más detalles
        val api = RetrofitClient.retrofit.create(PokeApiService::class.java)
        val callGetPokemonDetails = api.getPokemonDetailsByUrl(item.url)

        callGetPokemonDetails.enqueue(object : retrofit2.Callback<PokemonDetailsDTO> {
            override fun onResponse(call: Call<PokemonDetailsDTO>, response: Response<PokemonDetailsDTO>) {
                val pokemonDetails = response.body()

                if (pokemonDetails != null) {
                    val imageUrl = pokemonDetails.sprites.front_default

                    if (!imageUrl.isNullOrBlank()){
                        Glide.with(context)
                            .load(imageUrl)
                            .into(holder.ivPokemonIcon)
                    }
                }
            }

            override fun onFailure(call: Call<PokemonDetailsDTO>, t: Throwable) {
                Log.e("Error", t.message ?: " ")
            }
        })

        holder.itemView.setOnClickListener(View.OnClickListener {
            val context = holder.itemView.context
            val pokemonDetailsIntent = Intent(context,PokemonDetailsActivity::class.java)
            pokemonDetailsIntent.putExtra(Constants.POKEMON_NAME, item.name)
            context.startActivity(pokemonDetailsIntent)
        })
    }

    fun updateData(newData: List<PokemonDTO>) {
        pokemonList.clear()
        pokemonList.addAll(newData)
        notifyDataSetChanged()
    }

    fun addData(newData: List<PokemonDTO>) {
        pokemonList.addAll(newData)
        notifyDataSetChanged()
    }

    class PokemonViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        val txtPokemonName: TextView
        val txtPokemonUrl: TextView
        val ivPokemonIcon: ImageView
        init {
            txtPokemonName = view.findViewById(R.id.tvPokemonName)
            txtPokemonUrl = view.findViewById(R.id.tvPokemonUrl)
            ivPokemonIcon = view.findViewById(R.id.ivPokemonIcon)
        }
    }
}