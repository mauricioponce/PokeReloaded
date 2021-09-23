package cl.eme.pokemonesreloaded.di

import android.app.Application
import androidx.room.Room
import cl.eme.pokemonesreloaded.PokeViewModel
import cl.eme.pokemonesreloaded.data.Repository
import cl.eme.pokemonesreloaded.data.RepositoryImp
import cl.eme.pokemonesreloaded.data.datasources.LocalDataSource
import cl.eme.pokemonesreloaded.data.datasources.LocalDataSourceImp
import cl.eme.pokemonesreloaded.data.datasources.RemoteDataSource
import cl.eme.pokemonesreloaded.data.datasources.RemoteDataSourceImp
import cl.eme.pokemonesreloaded.data.db.PokeDAO
import cl.eme.pokemonesreloaded.data.db.PokeDatabase
import cl.eme.pokemonesreloaded.data.remote.PokeAPI
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    // single instances of datasources
    single<LocalDataSource> { LocalDataSourceImp(get())}
    single<RemoteDataSource> { RemoteDataSourceImp(get())}

    // single instance of CounterRepository
    single<Repository> { RepositoryImp(get(), get()) }

    // MyViewModel ViewModel
    viewModel { PokeViewModel(get()) }
}

val networkModule = module {
    factory { provideCounterAPI(get()) }
    single { provideRetrofit() }
}


//TODO move url to config
fun provideRetrofit(): Retrofit =
    Retrofit.Builder().baseUrl("https://lapi-pokemon.herokuapp.com/")
        .addConverterFactory(GsonConverterFactory.create()).build()


fun provideCounterAPI(retrofit: Retrofit): PokeAPI = retrofit.create(PokeAPI::class.java)


val databaseModule = module {
    single { provideDatabase(androidApplication()) }
    single { provideCountersDao(get())}
}

fun provideDatabase(application: Application): PokeDatabase {
    return Room.databaseBuilder(application, PokeDatabase::class.java, "db_poke").build()
}

fun provideCountersDao(database: PokeDatabase) : PokeDAO {
    return database.pokeDao()
}