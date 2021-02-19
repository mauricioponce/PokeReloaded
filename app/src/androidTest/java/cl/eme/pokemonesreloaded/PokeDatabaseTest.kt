package cl.eme.pokemonesreloaded

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import cl.eme.pokemonesreloaded.data.db.PokeDAO
import cl.eme.pokemonesreloaded.data.db.PokeDatabase
import cl.eme.pokemonesreloaded.data.db.PokemonEntity
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Before
import org.junit.Rule

@RunWith(AndroidJUnit4::class)
class PokeDatabaseTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var pokeDAO: PokeDAO
    private lateinit var pokeDB: PokeDatabase

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        pokeDB = Room.inMemoryDatabaseBuilder(context, PokeDatabase::class.java).build()
        pokeDAO = pokeDB.pokeDao()
    }

    @After
    fun tearDown() {
        pokeDB.close()
    }

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertThat(appContext.packageName).isEqualTo("cl.eme.pokemonesreloaded")
    }

    @Test
    fun insertPokemon_empty() = runBlocking {
        // Given
        val pokeList = listOf<PokemonEntity>()

        // When
        pokeDAO.insertPokemones(pokeList)

        // Then
        pokeDAO.getPokemones().observeForever {
            assertThat(it).isNotNull()
            assertThat(it).isEmpty()
        }
    }
}