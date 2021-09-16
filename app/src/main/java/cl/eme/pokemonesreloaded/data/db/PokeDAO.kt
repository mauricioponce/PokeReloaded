package cl.eme.pokemonesreloaded.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PokeDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemones(pokemones: List<PokemonEntity>)

    @Query("SELECT * FROM poke_entity")
    fun getPokemones(): Flow<List<PokemonEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDetail(pokemones: PokemonDetailEntity)

    @Query("SELECT * FROM poke_detail WHERE id=:pid")
    fun getPokemon(pid: String): LiveData<PokemonDetailEntity>
}