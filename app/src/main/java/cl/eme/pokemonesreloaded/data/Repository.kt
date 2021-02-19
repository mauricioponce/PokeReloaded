package cl.eme.pokemonesreloaded.data

import android.util.Log
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import cl.eme.pokemonesreloaded.data.db.PokeApplication
import cl.eme.pokemonesreloaded.data.db.PokemonDetailEntity
import cl.eme.pokemonesreloaded.data.db.PokemonEntity
import cl.eme.pokemonesreloaded.data.pojo.Pokemon
import cl.eme.pokemonesreloaded.data.pojo.PokemonDetail
import cl.eme.pokemonesreloaded.data.remote.RetrofitClient

class Repository {

    private val pokeDB = PokeApplication.pokeDb!!.pokeDao()

    fun pokelist(): LiveData<List<Pokemon>> = Transformations.map(pokeDB.getPokemones()) {
        it.map { entity -> db2api(entity) }
    }


    fun getDetailFromDB(pid: String): LiveData<PokemonDetail> = Transformations.map(pokeDB.getPokemon(pid)) {
        it?.let { db2api(it) }
    }

    suspend fun getPokemones() {
        val response = RetrofitClient.retrofitInstance().getPokemones()

        response.let {
            when (it.isSuccessful) {
                true -> {
                    response.body()?.let { pokeList ->
                        val map = pokeList.map { poke -> api2db(poke) }
                        pokeDB.insertPokemones(map)
                    }

                }
                false -> Log.d("PokeViewModel", "epa! error")
            }
        }
    }

    suspend fun getDetail(id: String) {
        // parchamos por culpa de API "·$"·$"%·!
        val pid = id.replace("#", "").toInt() - 1

        val response = RetrofitClient.retrofitInstance().getPokemon(pid.toString())
        Log.d("PokeViewModel", "cargando detalle para $pid")

        Log.d("PokeViewModel", response.body().toString())

        if (response.isSuccessful) {
            response.body()?.let { detail ->
                pokeDB.insertDetail(api2db(detail))
            }

        } else {
            Log.d("PokeViewModel", "epa! error en el detalle ${response.code()}")
        }
    }
}

fun api2db(pokemon: Pokemon): PokemonEntity {
    return PokemonEntity(pokemon.id, pokemon.img, pokemon.name)
}

fun db2api(pokemonEntity: PokemonEntity): Pokemon {
    return Pokemon(pokemonEntity.id, pokemonEntity.img, pokemonEntity.name)
}

fun api2db(detail: PokemonDetail): PokemonDetailEntity {
    return PokemonDetailEntity(detail.id, detail.img, detail.name, detail.labels)
}

fun db2api(detail: PokemonDetailEntity): PokemonDetail {
    return PokemonDetail(detail.id, detail.img, detail.name, detail.labels)
}