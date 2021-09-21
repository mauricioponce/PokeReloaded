package cl.eme.pokemonesreloaded

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope

import cl.eme.pokemonesreloaded.data.Repository
import cl.eme.pokemonesreloaded.data.datasources.LocalDataSourceImp
import cl.eme.pokemonesreloaded.data.datasources.RemoteDataSourceImp
import cl.eme.pokemonesreloaded.data.pojo.Pokemon
import cl.eme.pokemonesreloaded.data.pojo.PokemonDetail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class PokeViewModel : ViewModel() {

    private val repository = Repository(LocalDataSourceImp(), RemoteDataSourceImp())

    fun pokelist() : LiveData<List<Pokemon>> = repository.pokelist().asLiveData()

    fun getPokemones() {
        viewModelScope.launch {
            repository.fetchPokemones()
        }
    }

    fun getDetail(id: String): LiveData<PokemonDetail> = repository.getDetail(id).asLiveData()

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