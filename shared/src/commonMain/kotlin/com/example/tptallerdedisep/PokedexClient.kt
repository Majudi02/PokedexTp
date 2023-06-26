package com.example.tptallerdedisep

interface PokedexClient {
    suspend fun getPokedex(): Pokedex
}