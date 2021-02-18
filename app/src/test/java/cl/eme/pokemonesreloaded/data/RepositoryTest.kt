package cl.eme.pokemonesreloaded.data

import cl.eme.pokemonesreloaded.data.db.PokemonEntity
import cl.eme.pokemonesreloaded.data.pojo.Pokemon
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class RepositoryTest  {


    @Test
    fun db2Api_happyCase() {
        // Given
        val entity = PokemonEntity("1", "img1", "poke1")
        val expected = Pokemon("1", "img1", "poke1")

        // When
        val result = db2api(entity)

        // Then
        assertThat(result).isNotNull()
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun api2db_happyCase() {
        // Given
        val pokemon = Pokemon("1", "img1", "poke1")
        val expected = PokemonEntity("1", "img1", "poke1")

        // When
        val result = api2db(pokemon)

        // Then
        assertThat(result).isNotNull()
        assertThat(result).isEqualTo(expected)
    }
}