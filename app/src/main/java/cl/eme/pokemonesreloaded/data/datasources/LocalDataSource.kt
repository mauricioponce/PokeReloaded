package cl.eme.pokemonesreloaded.data.datasources

import cl.eme.pokemonesreloaded.data.db.PokeDAO
import cl.eme.pokemonesreloaded.data.db.PokemonDetailEntity
import cl.eme.pokemonesreloaded.data.db.PokemonEntity
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    fun getPokemones(): Flow<List<PokemonEntity>>

    fun getPokemon(pid: String): Flow<PokemonDetailEntity?>

    suspend fun insertDetail(detail: PokemonDetailEntity)

    suspend fun insertPokemons(pokemons: List<PokemonEntity>)
}

class LocalDataSourceImp(private val pokeDAO: PokeDAO) : LocalDataSource{
    override fun getPokemones(): Flow<List<PokemonEntity>> = pokeDAO.getPokemones()

    override fun getPokemon(pid: String): Flow<PokemonDetailEntity> = pokeDAO.getPokemon(pid)

    override suspend fun insertDetail(detail: PokemonDetailEntity) = pokeDAO.insertDetail(detail)

    override suspend fun insertPokemons(pokemons: List<PokemonEntity>) = pokeDAO.insertPokemones(pokemons)
}