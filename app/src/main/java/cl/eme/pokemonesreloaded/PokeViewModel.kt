package cl.eme.pokemonesreloaded

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.eme.pokemonesreloaded.data.Pokemon
import cl.eme.pokemonesreloaded.data.PokemonDetail
import cl.eme.pokemonesreloaded.data.Repository
import cl.eme.pokemonesreloaded.data.RetrofitClient
import kotlinx.coroutines.launch

class PokeViewModel : ViewModel() {

    private val repository = Repository()
    fun pokelist() : LiveData<List<Pokemon>> = repository.pokelist()

    fun getPokemones() {
        viewModelScope.launch {
            repository.getPokemones()
        }
    }


    fun getDetail(): LiveData<PokemonDetail> = repository.getDetail()

    fun getDetail(id: String) {

        viewModelScope.launch {
            repository.getDetail(id)
        }
    }


    private lateinit var selected : Pokemon

    fun setSelected(pokemon: Pokemon) {
        selected = pokemon
    }

    fun getSelected() = selected
}