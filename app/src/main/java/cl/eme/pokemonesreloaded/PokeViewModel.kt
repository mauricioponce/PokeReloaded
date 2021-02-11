package cl.eme.pokemonesreloaded

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.eme.pokemonesreloaded.data.RetrofitClient
import kotlinx.coroutines.launch

class PokeViewModel : ViewModel() {


    fun getPokemones() {
        viewModelScope.launch {
            val response = RetrofitClient.retrofitInstance().getPokemones()

            Log.d("PokeViewModel", "cargando info")
            response?.body()?.get(1)?.let { Log.d("PokeViewModel", it?.toString()) }
        }
    }
}