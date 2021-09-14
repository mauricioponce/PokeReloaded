package cl.eme.pokemonesreloaded.data.datasources

import androidx.lifecycle.LiveData
import cl.eme.pokemonesreloaded.data.db.PokeApplication
import cl.eme.pokemonesreloaded.data.db.PokemonDetailEntity
import cl.eme.pokemonesreloaded.data.db.PokemonEntity

class LocalDataSource {
    private val pokeDB = PokeApplication.pokeDb!!.pokeDao()

    fun getPokemones(): LiveData<List<PokemonEntity>> = pokeDB.getPokemones()

    fun getPokemon(pid: String): LiveData<PokemonDetailEntity> = pokeDB.getPokemon(pid)

    suspend fun insertDetail(detail: PokemonDetailEntity) = pokeDB.insertDetail(detail)

    suspend fun insertPokemons(pokemons: List<PokemonEntity>) = pokeDB.insertPokemones(pokemons)
}