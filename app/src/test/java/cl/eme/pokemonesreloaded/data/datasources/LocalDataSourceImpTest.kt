package cl.eme.pokemonesreloaded.data.datasources

import cl.eme.pokemonesreloaded.data.db.PokeDAO
import cl.eme.pokemonesreloaded.data.db.PokemonDetailEntity
import cl.eme.pokemonesreloaded.data.db.PokemonEntity
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

class LocalDataSourceImpTest {

    private lateinit var localDataSourceImp: LocalDataSource

    private lateinit var pokeDAO: PokeDAO

    private val expectedPokemonEntity = PokemonEntity("pik1", "http://www.false.com/img_1.jpg", "Pokemon 1")
    private val expectedDetailPokemonEntity = PokemonDetailEntity("pik1", "http://www.false.com/img_1.jpg", "Pokemon 1", listOf("label1"))

    @Before
    fun setUp() {
        pokeDAO = FakePokeDao()
        localDataSourceImp = LocalDataSourceImp(pokeDAO)

    }

    @Test
    fun `getPokemones happy case`() = runBlockingTest{
        // given
        localDataSourceImp.insertPokemons(listOf(expectedPokemonEntity))

        // when
        val result = localDataSourceImp.getPokemones()

        // then
        assertThat(result.count()).isAtLeast(1)
        result.collect {
            assertThat(it).isNotNull()
            assertThat(it).hasSize(1)
        }
    }

    @Test
    fun `getPokemon happy case`() = runBlockingTest {
        // given
        val requestPid = expectedDetailPokemonEntity.id
        localDataSourceImp.insertDetail(expectedDetailPokemonEntity)

        // when
        val result = localDataSourceImp.getPokemon(requestPid)

        // then
        assertThat(result.count()).isAtLeast(1)
        result.collect {
            assertThat(it).isNotNull()
            assertThat(it).isEqualTo(expectedDetailPokemonEntity)
        }
    }

    @Test
    fun `insertDetail happy case`() {
    }

    @Test
    fun `insertPokemons happy case`() {
    }
}

class FakePokeDao : PokeDAO {
    private val pokeStorage: MutableList<PokemonEntity> = mutableListOf()
    private val pokeDetailStorage: MutableList<PokemonDetailEntity> = mutableListOf()

    override suspend fun insertPokemones(pokemones: List<PokemonEntity>) {
        pokeStorage.addAll(pokemones)
    }

    override fun getPokemones(): Flow<List<PokemonEntity>> {
        return flowOf(pokeStorage)
    }

    override suspend fun insertDetail(pokemones: PokemonDetailEntity) {
        pokeDetailStorage.add(pokemones)
    }

    override fun getPokemon(pid: String): Flow<PokemonDetailEntity> {
        val result = pokeDetailStorage.first { it.id == pid }
        return flowOf(result)
    }
}
