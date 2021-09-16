package cl.eme.pokemonesreloaded.data

import cl.eme.pokemonesreloaded.data.datasources.LocalDataSource
import cl.eme.pokemonesreloaded.data.datasources.RemoteDataSource
import cl.eme.pokemonesreloaded.data.db.PokemonEntity
import cl.eme.pokemonesreloaded.data.pojo.Pokemon
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import java.lang.Exception

class RepositoryTest {

    lateinit var repository: Repository

    @MockK
    lateinit var localDataSource: LocalDataSource

    @MockK
    lateinit var remoteDataSource: RemoteDataSource


    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        repository = Repository(localDataSource, remoteDataSource)
    }

    @Test
    fun `getPokemones happy case`() = runBlocking {
        // given
        val expectedList = listOf(Pokemon("pik1", "http://www.false.com/img_1.jpg", "Pokemon 1"))
        val entitiesList = listOf(PokemonEntity("pik1", "http://www.false.com/img_1.jpg", "Pokemon 1"))

        coEvery { localDataSource.getPokemones() } returns flow { emit(entitiesList) }
        coEvery { remoteDataSource.getPokemones() } returns expectedList
        coEvery { localDataSource.insertPokemons(any()) } returns Unit

        // when
        repository.fetchPokemones()

        // then

        repository.pokelist().collect {
            assertThat(it).isNotNull()
            assertThat(it).hasSize(1)
        }
    }

    @Test
    fun `getPokemones empty list`() = runBlocking {
        // given
        val expectedList = emptyList<Pokemon>()

        coEvery { remoteDataSource.getPokemones() } returns expectedList
        coEvery { localDataSource.getPokemones() } returns flow { emit(emptyList<PokemonEntity>()) }
        coEvery { localDataSource.insertPokemons(any()) } returns Unit

        // when
        repository.fetchPokemones()

        // then
        assertThat(repository.pokelist().count()).isEqualTo(1)
        repository.pokelist().collect {
            assertThat(it).isNotNull()
            assertThat(it).isEmpty()
        }
    }


    @Test
    fun `getPokemones error`() = runBlocking {
        // given
        val expectedList = listOf(Pokemon("pik1", "http://www.false.com/img_1.jpg", "Pokemon 1"))
        val entitiesList = listOf(PokemonEntity("pik1", "http://www.false.com/img_1.jpg", "Pokemon 1"))

        coEvery { localDataSource.getPokemones() } returns flow { emit(entitiesList) }
        coEvery { remoteDataSource.getPokemones() } throws Exception()
        coEvery { localDataSource.insertPokemons(any()) } returns Unit

        // when
        runCatching { repository.fetchPokemones() }

        // then
        repository.pokelist().collect {
            assertThat(it).isNotNull()
            assertThat(it).hasSize(1)
        }
    }
}