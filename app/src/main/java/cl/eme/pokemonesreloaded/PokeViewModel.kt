package cl.eme.pokemonesreloaded

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope

import cl.eme.pokemonesreloaded.data.Repository
import cl.eme.pokemonesreloaded.data.datasources.LocalDataSource
import cl.eme.pokemonesreloaded.data.datasources.RemoteDataSource
import cl.eme.pokemonesreloaded.data.pojo.Pokemon
import cl.eme.pokemonesreloaded.data.pojo.PokemonDetail
import kotlinx.coroutines.launch

class PokeViewModel : ViewModel() {

    private val repository = Repository(LocalDataSource(), RemoteDataSource())

    fun pokelist() : LiveData<List<Pokemon>> = repository.pokelist().asLiveData()

    fun getPokemones() {
        viewModelScope.launch {
            repository.fetchPokemones()
        }
    }

    // cuando te llegue el resultado, yo voy a ....
    fun getDetail(id: String): LiveData<PokemonDetail> = repository.getDetailFromDB(id)

    // consume o anda a buscar el valor para este ID
    fun consumeDetail(id: String) {
        viewModelScope.launch {
            repository.fetchDetail(id)
        }
    }


    private lateinit var selected : Pokemon

    fun setSelected(pokemon: Pokemon) {
        selected = pokemon
    }

    fun getSelected() = selected
}