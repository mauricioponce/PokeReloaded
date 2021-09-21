package cl.eme.pokemonesreloaded.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import cl.eme.pokemonesreloaded.data.datasources.LocalDataSource
import cl.eme.pokemonesreloaded.data.datasources.RemoteDataSource
import cl.eme.pokemonesreloaded.data.pojo.Pokemon
import cl.eme.pokemonesreloaded.data.pojo.PokemonDetail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest

class Repository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) {

    fun pokelist(): Flow<List<Pokemon>> = localDataSource.getPokemones().mapLatest { it.map { entity -> db2api(entity) } }

    fun getDetail(pid: String): LiveData<PokemonDetail> = Transformations.map(localDataSource.getPokemon(pid)) {
        it?.let { db2api(it) }
    }

    suspend fun fetchPokemones() {
        val pokeList = remoteDataSource.fetchPokemones()
        val map = pokeList.map { poke -> api2db(poke) }
        localDataSource.insertPokemons(map)
    }

    suspend fun fetchDetail(id: String) {
        val detail = remoteDataSource.fetchDetail(id)
        detail?.let {
            val r = api2db(it)
            localDataSource.insertDetail(r)
        }
    }
}
