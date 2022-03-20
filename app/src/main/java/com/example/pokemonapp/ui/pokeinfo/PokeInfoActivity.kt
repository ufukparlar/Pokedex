package com.example.pokemonapp.ui.pokeinfo

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.pokemonapp.R
import com.example.pokemonapp.database.DatabaseHandler
import com.example.pokemonapp.database.PokemonFavourites
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_pokeinfo.*
import kotlin.properties.Delegates

class PokeInfoActivity : AppCompatActivity() {

    lateinit var viewModel: PokeInfoViewModel
    private var id by Delegates.notNull<Int>()
    private var isFav by Delegates.notNull<Boolean>()


    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokeinfo)

        id= intent.extras?.get("id") as Int
        viewModel = ViewModelProvider(this).get(PokeInfoViewModel::class.java)



        initUI()
        isFavourite()

    }

    private fun isFavourite() {
        val db = DatabaseHandler(this)

        Log.d("favstat",db.checkFav(id).toString())
        val result = db.checkFav(id)
        val pokeid = PokemonFavourites(id)

        if(result == 0)
        {
            //Not favourite
            // Show add favourite button
            favoriteBtn.setText("Add To Favourite")

            favoriteBtn.setOnClickListener()
            {
                //Add to fav
                db.addFav(pokeid)

                //REFRESH PAGE
                finish()
                startActivity(intent)
            }

        }
        else {
            // Show remove favourite button
            favoriteBtn.setText("Remove Favourite")
            favoriteBtn.setOnClickListener()
            {
                //Rremove Favourite
                db.deletefav(pokeid)

                finish()
                startActivity(intent)

            }
        }
    }

    private fun initUI(){


        viewModel.getPokemonInfo(id)

        viewModel.pokemonInfo.observe(this, Observer { pokemon ->
            nameTextView.text = pokemon.name
            heightText.text = "Height: ${pokemon.height}cm"
            weightText.text = "Weight: ${pokemon.weight}gr"

            Glide.with(this).load(pokemon.sprites.frontDefault).into(imageView)
        })
    }



}