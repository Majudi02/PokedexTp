package com.example.tptallerdedisep

import com.pokedex.db.AppDatabase

class PokedexDBRepository(databaseDriverFactory: DatabaseDriverFactory) {

    private val database=  AppDatabase(databaseDriverFactory.createDriver())
    private val dbQueries = database.pokedexQueries

    fun insert(pokemon:PokedexResults){
        dbQueries.insertPokemon(pokemon.name,pokemon.url)
    }



}



