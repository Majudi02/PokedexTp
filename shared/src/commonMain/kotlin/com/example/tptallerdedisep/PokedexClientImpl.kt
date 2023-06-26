package com.example.tptallerdedisep

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class PokedexClientImpl(private val httpClient: HttpClient) : PokedexClient {
    override suspend fun getPokedex(): Pokedex {
        val response= httpClient.get("https://pokeapi.co/api/v2/pokemon/?limit=800")
        return response.body()
    }
}