package com.example.tptallerdedisep

import com.pokedex.db.AppDatabase

class PokedexDBRepository(databaseDriverFactory: DatabaseDriverFactory) {

    private val database=  AppDatabase(databaseDriverFactory.createDriver())



}



