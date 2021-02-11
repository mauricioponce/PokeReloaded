package cl.eme.pokemonesreloaded.data

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
//https://lapi-pokemon.herokuapp.com/pokemon
//https://lapi-pokemon.herokuapp.com/pokemon/1
/*
*

 */

interface PokeAPI {
    @GET("pokemon")
    suspend fun getPokemones(): Response<List<Pokemon>>
}

class RetrofitClient {
    companion object {
        private const val BASE_URL =  "https://lapi-pokemon.herokuapp.com/"

        fun retrofitInstance(): PokeAPI {
            val retrofit = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(
                GsonConverterFactory.create())
                .build()

            return retrofit.create(PokeAPI::class.java)
        }
    }
}

