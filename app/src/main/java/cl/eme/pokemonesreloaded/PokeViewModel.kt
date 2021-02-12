package cl.eme.pokemonesreloaded

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.eme.pokemonesreloaded.data.Pokemon
import cl.eme.pokemonesreloaded.data.PokemonDetail
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


    private val detail = MutableLiveData<PokemonDetail>()
    fun getDetail(): LiveData<PokemonDetail> = detail

    fun getDetail(id: String) {

        // parchamos por culpa de API "·$"·$"%·!
        val pid = id.replace("#", "").toInt() - 1


        viewModelScope.launch {
            val response = RetrofitClient.retrofitInstance().getPokemon(pid.toString())
            Log.d("PokeViewModel", "cargando detalle para $pid")

            Log.d("PokeViewModel", response.body().toString())

            if(response.isSuccessful) {
                detail.value = response.body()
            } else {
                Log.d("PokeViewModel", "epa! error en el detalle ${response.code()}")
            }
        }
    }


    private lateinit var selected : Pokemon

    fun setSelected(pokemon: Pokemon) {
        selected = pokemon
    }

    fun getSelected() = selected
}