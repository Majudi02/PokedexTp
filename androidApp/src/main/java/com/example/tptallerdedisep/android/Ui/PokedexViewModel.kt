package com.example.tptallerdedisep.android.Ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tptallerdedisep.PokedexRepository
import com.example.tptallerdedisep.PokedexResults
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PokedexViewModel() : ViewModel() {

    val repositorio = PokedexRepository()

    private val _pokedex = MutableStateFlow(emptyList<PokedexResults>())
    val pokedex: StateFlow<List<PokedexResults>> get() = _pokedex

    private val coroutineExceptionHandler =
        CoroutineExceptionHandler { coroutineContext, throwable ->
            Log.d("PokedexViewModel", "Error retrieving pokedex: ${throwable.message}")
        }

    init {
        viewModelScope.launch(coroutineExceptionHandler) {
            kotlin.runCatching {
                repositorio.getPokedex1()
            }.onSuccess {
                if (it.isNotEmpty()) {
                    _pokedex.value = it
                } else {
                    _pokedex.value = emptyList()
                }
            }.onFailure {
                Log.d("PokedexViewModel", "Error retrieving pokedex: ${it.message}")
                _pokedex.value = emptyList()
            }

        }
    }
}
