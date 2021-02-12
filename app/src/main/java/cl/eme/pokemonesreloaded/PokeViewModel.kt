package cl.eme.pokemonesreloaded

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.eme.pokemonesreloaded.data.Pokemon
import cl.eme.pokemonesreloaded.data.RetrofitClient
import kotlinx.coroutines.launch

class PokeViewModel : ViewModel() {


    private val pokeList = MutableLiveData<List<Pokemon>>()

    fun pokelist() : LiveData<List<Pokemon>> = pokeList

    fun getPokemones() {
        viewModelScope.launch {
            val response = RetrofitClient.retrofitInstance().getPokemones()

            Log.d("PokeViewModel", "cargando info")

            response.let {
                when(it.isSuccessful) {
                    true -> pokeList.value = response.body()
                    false -> Log.d("PokeViewModel", "epa! error")
                }
            }
        }
    }
}