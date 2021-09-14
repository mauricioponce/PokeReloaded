package cl.eme.pokemonesreloaded.data.datasources

import android.util.Log
import cl.eme.pokemonesreloaded.data.pojo.Pokemon
import cl.eme.pokemonesreloaded.data.pojo.PokemonDetail
import cl.eme.pokemonesreloaded.data.remote.RetrofitClient

class RemoteDataSource {

    suspend fun getPokemones(): List<Pokemon> {
        val response = RetrofitClient.retrofitInstance().getPokemones()

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

    suspend fun getDetail(id: String): PokemonDetail? {
        // parchamos por culpa de API "·$"·$"%·!
        val pid = id.replace("#", "").toInt() - 1

        val response = RetrofitClient.retrofitInstance().getPokemon(pid.toString())
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