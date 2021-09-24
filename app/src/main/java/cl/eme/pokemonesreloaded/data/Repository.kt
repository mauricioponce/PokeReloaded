package cl.eme.pokemonesreloaded.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import cl.eme.pokemonesreloaded.data.datasources.LocalDataSource
import cl.eme.pokemonesreloaded.data.datasources.RemoteDataSource
import cl.eme.pokemonesreloaded.data.pojo.Pokemon
import cl.eme.pokemonesreloaded.data.pojo.PokemonDetail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.mapNotNull

interface Repository {
    fun pokelist(): Flow<List<Pokemon>>

    fun getDetail(pid: String): Flow<PokemonDetail>

    suspend fun fetchPokemones()

    suspend fun fetchDetail(id: String)
}

class RepositoryImp(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
): Repository {

    override fun pokelist(): Flow<List<Pokemon>> = localDataSource.getPokemones().mapLatest { it.map { entity -> db2api(entity) } }

    override fun getDetail(pid: String): Flow<PokemonDetail> =
        localDataSource.getPokemon(pid).mapNotNull {
            it?.let {
                db2api(it)
            }
        }

    override suspend fun fetchPokemones() {
        val pokeList = remoteDataSource.fetchPokemones()
        val map = pokeList.map { poke -> api2db(poke) }
        localDataSource.insertPokemons(map)
    }

    override suspend fun fetchDetail(id: String) {
        val detail = remoteDataSource.fetchDetail(id)
        detail?.let {
            val r = api2db(it)
            localDataSource.insertDetail(r)
        }
    }
}
