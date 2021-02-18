package cl.eme.pokemonesreloaded.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class Repository {

    private val pokeList = MutableLiveData<List<Pokemon>>()
    fun pokelist(): LiveData<List<Pokemon>> = pokeList

    private val detail = MutableLiveData<PokemonDetail>()
    fun getDetail(): LiveData<PokemonDetail> = detail

    suspend fun getPokemones() {

        val response = RetrofitClient.retrofitInstance().getPokemones()

        Log.d("PokeViewModel", "cargando info")

        response.let {
            when (it.isSuccessful) {
                true -> pokeList.value = response.body()
                false -> Log.d("PokeViewModel", "epa! error")
            }
        }
    }

    suspend fun getDetail(id: String) {

        // parchamos por culpa de API "·$"·$"%·!
        val pid = id.replace("#", "").toInt() - 1

        val response = RetrofitClient.retrofitInstance().getPokemon(pid.toString())
        Log.d("PokeViewModel", "cargando detalle para $pid")

        Log.d("PokeViewModel", response.body().toString())

        if (response.isSuccessful) {
            detail.value = response.body()
        } else {
            Log.d("PokeViewModel", "epa! error en el detalle ${response.code()}")
        }

    }
}