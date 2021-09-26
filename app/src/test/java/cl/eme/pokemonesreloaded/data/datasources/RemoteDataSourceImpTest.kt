package cl.eme.pokemonesreloaded.data.datasources

import cl.eme.pokemonesreloaded.data.pojo.Pokemon
import cl.eme.pokemonesreloaded.data.remote.PokeAPI
import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before

import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * valida el funcionamiento del datasource y además es un contrato
 * para validar los endpoints
 */
class RemoteDataSourceImpTest {

    private lateinit var remoteDataSource: RemoteDataSource

    private lateinit var mockWebServer: MockWebServer

    private lateinit var pokeAPI: PokeAPI

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        pokeAPI = Retrofit.Builder().baseUrl(mockWebServer.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PokeAPI::class.java)

        remoteDataSource = RemoteDataSourceImp(pokeAPI)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun fetchPokemones() = runBlocking{
        // Given
        val resultList = listOf(Pokemon("1", "pimg", "pokename"))
        mockWebServer.enqueue(MockResponse().setBody(Gson().toJson(resultList)))

        // When
        val result = remoteDataSource.fetchPokemones()

        // then
        with(assertThat(result)) {
            isNotNull()
            hasSize(1)
        }
    }

    @Test
    fun fetchDetail() {
    }
}