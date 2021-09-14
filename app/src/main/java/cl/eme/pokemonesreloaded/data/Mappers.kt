package cl.eme.pokemonesreloaded.data

import cl.eme.pokemonesreloaded.data.db.PokemonDetailEntity
import cl.eme.pokemonesreloaded.data.db.PokemonEntity
import cl.eme.pokemonesreloaded.data.pojo.Pokemon
import cl.eme.pokemonesreloaded.data.pojo.PokemonDetail


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