package com.example.tptallerdedisep

class PokedexDBRepository(databaseDriverFactory: DatabaseDriverFactory) {

    private val database=  AppDatabase(databaseDriverFactory.createDriver())



}



