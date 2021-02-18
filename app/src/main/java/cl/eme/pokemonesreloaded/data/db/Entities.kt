package cl.eme.pokemonesreloaded.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "poke_entity")
data class PokemonEntity(@PrimaryKey val id: String, val img: String, val name: String)

@Entity(tableName = "poke_detail")
data class PokemonDetailEntity(@PrimaryKey val id: String, val img: String, val name: String, val labels: List<String>)

