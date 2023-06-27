package com.example.tptallerdedisep

import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class PokedexRepository {


    val httpClient = HttpClient {
        install(Logging) {
            level = LogLevel.ALL
            logger = object : Logger {
                override fun log(message: String) {
                    Napier.v(tag = "HttpClient", message = message)
                }
            }
        }
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
             //       coerceInputValues = true
                }
            )
        }
    }.also{
        initLogger()
    }

    private val pokedexClient = PokedexClientImpl(httpClient)

    suspend fun getPokedex1(): List<PokedexResults> {
        return pokedexClient.getPokedex().results

    }


    suspend fun getPokedex(): List<PokedexResults> {
        val results = httpClient.get("https://pokeapi.co/api/v2/pokemon/?limit=100")
        return results.body()
   /*  val response : HttpResponse = httpClient.get("https://pokeapi.co/api/v2/pokemon/?limit=800")
        if(response.status.isSuccess()){
            val responseContent: String = response.bodyAsText()
            val pokedex:Pokedex = Json.decodeFromString(responseContent)
            return pokedex.results
        }else{
            throw Exception("Error al obtener la lista de objetos de la Pokédex")
        }
    */
    }
}