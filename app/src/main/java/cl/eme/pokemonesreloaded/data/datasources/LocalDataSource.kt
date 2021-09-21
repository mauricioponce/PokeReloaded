package cl.eme.pokemonesreloaded.data.datasources

import androidx.lifecycle.LiveData
import cl.eme.pokemonesreloaded.data.db.PokeApplication
import cl.eme.pokemonesreloaded.data.db.PokemonDetailEntity
import cl.eme.pokemonesreloaded.data.db.PokemonEntity
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    fun getPokemones(): Flow<List<PokemonEntity>>

    fun getPokemon(pid: String): Flow<PokemonDetailEntity>

    suspend fun insertDetail(detail: PokemonDetailEntity)

    suspend fun insertPokemons(pokemons: List<PokemonEntity>)
}

class LocalDataSourceImp : LocalDataSource{
    private val pokeDB = PokeApplication.pokeDb!!.pokeDao()

    override fun getPokemones(): Flow<List<PokemonEntity>> = pokeDB.getPokemones()

    override fun getPokemon(pid: String): Flow<PokemonDetailEntity> = pokeDB.getPokemon(pid)

    override suspend fun insertDetail(detail: PokemonDetailEntity) = pokeDB.insertDetail(detail)

    override suspend fun insertPokemons(pokemons: List<PokemonEntity>) = pokeDB.insertPokemones(pokemons)
}