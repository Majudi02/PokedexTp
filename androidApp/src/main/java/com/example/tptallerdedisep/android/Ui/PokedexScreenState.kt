package com.example.tptallerdedisep.android.Ui

import com.example.tptallerdedisep.Pokedex


sealed class PokedexScreenState {
    object Loading : PokedexScreenState()

    object Error : PokedexScreenState()

    class ShowPokedex(val pokedex : Pokedex) : PokedexScreenState()
}
