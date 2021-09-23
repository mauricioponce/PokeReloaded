package cl.eme.pokemonesreloaded.data.datasources

import cl.eme.pokemonesreloaded.data.db.PokeDAO
import cl.eme.pokemonesreloaded.data.db.PokemonDetailEntity
import cl.eme.pokemonesreloaded.data.db.PokemonEntity
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    fun getPokemones(): Flow<List<PokemonEntity>>

    fun getPokemon(pid: String): Flow<PokemonDetailEntity>

    suspend fun insertDetail(detail: PokemonDetailEntity)

    suspend fun insertPokemons(pokemons: List<PokemonEntity>)
}

class LocalDataSourceImp(private val pokeDB: PokeDAO) : LocalDataSource{
    override fun getPokemones(): Flow<List<PokemonEntity>> = pokeDB.getPokemones()

    override fun getPokemon(pid: String): Flow<PokemonDetailEntity> = pokeDB.getPokemon(pid)

    override suspend fun insertDetail(detail: PokemonDetailEntity) = pokeDB.insertDetail(detail)

    override suspend fun insertPokemons(pokemons: List<PokemonEntity>) = pokeDB.insertPokemones(pokemons)
}