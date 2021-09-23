package cl.eme.pokemonesreloaded.data.datasources

import android.util.Log
import cl.eme.pokemonesreloaded.data.pojo.Pokemon
import cl.eme.pokemonesreloaded.data.pojo.PokemonDetail
import cl.eme.pokemonesreloaded.data.remote.PokeAPI
import cl.eme.pokemonesreloaded.data.remote.RetrofitClient
import retrofit2.Retrofit

interface RemoteDataSource {
    suspend fun fetchPokemones(): List<Pokemon>

    suspend fun fetchDetail(id: String): PokemonDetail?
}

class RemoteDataSourceImp(private val retrofitInstance: PokeAPI): RemoteDataSource {
    override suspend fun fetchPokemones(): List<Pokemon> {
        val response = retrofitInstance.getPokemones()

        val a = with(response) {
            when (isSuccessful) {
                true -> {
                    body()?.let { pokeList ->
                        pokeList
                    }
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

        val response = retrofitInstance.getPokemon(pid.toString())
        Log.d("PokeViewModel", "cargando detalle para $pid")

        Log.d("PokeViewModel", response.body().toString())
        val a = if (response.isSuccessful) {
            response.body()?.let { detail ->
                detail
            }
        } else {
            null
        }
        return a
    }
}