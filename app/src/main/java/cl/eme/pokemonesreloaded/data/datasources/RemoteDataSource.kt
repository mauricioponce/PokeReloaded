package cl.eme.pokemonesreloaded.data.datasources

import cl.eme.pokemonesreloaded.data.pojo.Pokemon
import cl.eme.pokemonesreloaded.data.pojo.PokemonDetail
import cl.eme.pokemonesreloaded.data.remote.PokeAPI

interface RemoteDataSource {
    suspend fun fetchPokemones(): List<Pokemon>

    suspend fun fetchDetail(id: String): PokemonDetail?
}

class RemoteDataSourceImp(private val pokeAPI: PokeAPI): RemoteDataSource {
    override suspend fun fetchPokemones(): List<Pokemon> {
        val response = pokeAPI.getPokemones()

        val a = with(response) {
            when (isSuccessful) {
                true -> {
                    body()
                }

                false -> {
                    emptyList()
                }
            }
        }

        return a!!
    }

    override suspend fun fetchDetail(id: String): PokemonDetail? {
        // parchamos por culpa de API "·$"·$"%·!
        val pid = id.replace("#", "").toInt() - 1

        val response = pokeAPI.getPokemon(pid.toString())

        val a = if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
        return a
    }
}