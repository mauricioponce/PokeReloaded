package cl.eme.pokemonesreloaded

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import cl.eme.pokemonesreloaded.data.pojo.Pokemon
import cl.eme.pokemonesreloaded.data.remote.PokeAPI
import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Test

import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(JUnit4::class)
class PokeAPITest {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var mockWebServer: MockWebServer

    private lateinit var service: PokeAPI

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        service = Retrofit.Builder().baseUrl(mockWebServer.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PokeAPI::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun pokeList_happyCase() = runBlocking {
        // Given
        val resultList = listOf(Pokemon("1", "pimg", "pokename"))
        mockWebServer.enqueue(MockResponse().setBody(Gson().toJson(resultList)))

        // When
        val result = service.getPokemones()

        // Then
        assertThat(result).isNotNull()
        assertThat(result.isSuccessful).isTrue()

        assertThat(result.body()).hasSize(1)
    }
}