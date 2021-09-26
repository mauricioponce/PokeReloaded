package cl.eme.pokemonesreloaded.data.remote

import cl.eme.pokemonesreloaded.data.pojo.Pokemon
import cl.eme.pokemonesreloaded.data.pojo.PokemonDetail
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PokeAPI {
    @GET("pokemon")
    suspend fun getPokemones(): Response<List<Pokemon>>

    @GET("pokemon/{pid}")
    suspend fun getPokemon(@Path("pid") id: String): Response<PokemonDetail>
}

