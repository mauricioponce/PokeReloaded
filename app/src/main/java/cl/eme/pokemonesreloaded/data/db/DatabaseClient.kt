package cl.eme.pokemonesreloaded.data.db

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [PokemonEntity::class, PokemonDetailEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class PokeDatabase: RoomDatabase() {
    abstract fun pokeDao(): PokeDAO
}


