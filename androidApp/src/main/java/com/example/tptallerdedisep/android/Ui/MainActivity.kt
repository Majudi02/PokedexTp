package com.example.tptallerdedisep.android.Ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import coil.compose.AsyncImage
import com.example.tptallerdedisep.DatabaseDriverFactory
import com.example.tptallerdedisep.PokedexDBRepository
import com.example.tptallerdedisep.PokedexResults
import com.example.tptallerdedisep.android.Domain.PokedexViewModel
import com.example.tptallerdedisep.android.ImageBuilder
import com.example.tptallerdedisep.android.MyApplicationTheme

class MainActivity : ComponentActivity() {
   // val viewModel = PokedexViewModel()
    private val viewModel : PokedexViewModel by viewModels()


    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    PokedexScreen()
                }
            }
        }
    }


 /*   @Composable
    fun test(){
        val pokemon =viewModel.nombre.value

        Text(pokemon.toString())
    } */

    @Composable
    fun PokedexScreen() {
        // Listen to Retrofit response
        val pokedexDBRepository = PokedexDBRepository(DatabaseDriverFactory(this@MainActivity))
        val pokedexResults by viewModel.pokedex.collectAsState()

        LazyColumn {
            if (pokedexResults.isEmpty()) {
                val resultsFromDb = pokedexDBRepository.get()
                items(resultsFromDb) { result: PokedexResults ->
                    PokemonCard(pokemon = result)
                }
            } else {
                pokedexDBRepository.populate(pokedexResults)
                items(pokedexResults) { result: PokedexResults ->
                    PokemonCard(pokemon = result)
                }
            }
        }
    }
    @Composable
     fun vacio() {
     Text("Lista vacia")
    }

    @Composable
    fun PokemonCard(pokemon: PokedexResults) {
        Row {
            PokemonImageContainer(modifier = Modifier.size(64.dp)) {
                PokemonImage(pokemon)
            }
            Spacer(Modifier.width(20.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically)
            ) {
                Text(
                    text = pokemon.name,
                    style = MaterialTheme.typography.h6
                )
            }
            Divider(modifier = Modifier.padding(top = 10.dp))
        }
    }

    @Composable

    fun PokemonImage(pokemon: PokedexResults) {
        Box {
            AsyncImage(
                model = ImageBuilder.buildPokemonImageByUrl(pokemon.url),
                contentDescription = "Foto",
                alignment = Alignment.Center,
                modifier = Modifier
                    .size(110.dp)
                    .padding(top = 0.dp, start = 15.dp, end = 15.dp)
            )
        }
    }

    @Composable
    fun PokemonImageContainer(
        modifier: Modifier,
        content: @Composable () -> Unit
    ) {
        Surface(modifier.aspectRatio(1f), RoundedCornerShape(4.dp)) {
            content()
        }
    }

    /*
       @Composable
       fun Test(){
           viewModel = ViewModelProvider(this, PokedexViewModelFactory())[PokedexViewModel::class.java]
           val pokedexResults by viewModel.pokedex.collectAsState()

           LazyColumn(modifier = Modifier.fillMaxSize()) {
               for(i in 0..pokedexResults.size){
                   item {
                       Row(modifier = Modifier.fillMaxWidth()) {
                           AsyncImage(
                               model = ImageBuilder.buildPokemonImageByUrl(pokedexResults[i].url),
                               contentDescription = "fOTO",
                               modifier = Modifier.size(130.dp, 130.dp)
                           )
                           AsyncImage(
                               model = ImageBuilder.buildPokemonImageByUrl(pokedexResults[i].url),
                               contentDescription = "fOTO",
                               modifier = Modifier.size(130.dp, 130.dp)
                           )

                           AsyncImage(
                               model = ImageBuilder.buildPokemonImageByUrl(pokedexResults[i].url),
                               contentDescription = "fOTO",
                               modifier = Modifier.size(130.dp, 130.dp)
                           )
                       }
                   }
               }
           }
       }

        */
}




