package cl.eme.pokemonesreloaded.data

import cl.eme.pokemonesreloaded.data.datasources.LocalDataSource
import cl.eme.pokemonesreloaded.data.datasources.RemoteDataSource
import cl.eme.pokemonesreloaded.data.db.PokemonDetailEntity
import cl.eme.pokemonesreloaded.data.db.PokemonEntity
import cl.eme.pokemonesreloaded.data.pojo.Pokemon
import com.google.common.truth.Truth.assertThat
import com.google.common.truth.Truth.assertWithMessage
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

    private val expectedPokemon = Pokemon("pik1", "http://www.false.com/img_1.jpg", "Pokemon 1")
    private val expectedPokemonEntity = PokemonEntity("pik1", "http://www.false.com/img_1.jpg", "Pokemon 1")
    private val expectedPokemonDetailEntity = PokemonDetailEntity("pik1", "http://www.false.com/img_1.jpg", "Pokemon 1", listOf("fire"))


    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        repository = RepositoryImp(localDataSource, remoteDataSource)

        coEvery { localDataSource.insertPokemons(any()) } returns Unit
    }

    @Test
    fun `getPokemones happy case`() = runBlocking {
        // given
        val expectedList = listOf(expectedPokemon)
        val entitiesList = listOf(expectedPokemonEntity)

        coEvery { localDataSource.getPokemones() } returns flowOf(entitiesList)
        coEvery { remoteDataSource.fetchPokemones() } returns expectedList

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

        coEvery { remoteDataSource.fetchPokemones() } returns expectedList
        coEvery { localDataSource.getPokemones() } returns flowOf(emptyList())


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
    fun `getPokemones error at remote datasource`(): Unit = runBlocking {
        // given
        val entitiesList = listOf(expectedPokemonEntity)

        coEvery { localDataSource.getPokemones() } returns flowOf(entitiesList)
        coEvery { remoteDataSource.fetchPokemones() } throws Exception()

        // when
        runCatching { repository.fetchPokemones() }.onFailure {
            assertWithMessage("Failure from sources is not supported").that(false) }
            .onSuccess { // then
                repository.pokelist().collect {
                    assertThat(it).isNotNull()
                    assertThat(it).hasSize(1)
                }
            }
    }

    @Test
    fun `getDetail happy case`() = runBlocking {
        // given
        val id = "pid"

        coEvery { localDataSource.getPokemon(id) } returns flowOf(expectedPokemonDetailEntity)


        // when
        val result = repository.getDetail(id)

        // then
        result.collect {
            assertThat(it).isNotNull()
        }
    }

    @Test
    fun `getDetail null`() = runBlocking {
        // given
        val id = "pid"

        coEvery { localDataSource.getPokemon(id) } returns flow { null }

        // when
        val result = repository.getDetail(id)

        // then
        result.collect {
            assertThat(it).isNull()
        }
    }
}