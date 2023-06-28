package com.example.tptallerdedisep

import com.pokedex.db.AppDatabase

class PokedexDBRepository(databaseDriverFactory: DatabaseDriverFactory) {

    private val database = AppDatabase(databaseDriverFactory.createDriver())
    private val dbQuery = database.pokedexQueries

    fun populate(results: List<PokedexResults>) {
        dbQuery.transaction {
            results.forEach{ pokemon ->
                dbQuery.insertPokemon(
                    name = pokemon.name,
                    url = pokemon.url
                )
            }
        }
    }

    fun get() : List<PokedexResults> {
        val results : List<PokedexResults> = dbQuery.selectAllPokemon {
                name, url ->  PokedexResults(name, url)
        }.executeAsList()

        return results
    }

}
