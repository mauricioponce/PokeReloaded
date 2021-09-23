package cl.eme.pokemonesreloaded

import android.app.Application
import cl.eme.pokemonesreloaded.di.appModule
import cl.eme.pokemonesreloaded.di.databaseModule
import cl.eme.pokemonesreloaded.di.networkModule
import org.koin.core.context.startKoin
import org.koin.android.ext.koin.androidContext

class PokeApplication : Application() {
    /**
     * Start koin for dependencies
     */
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@PokeApplication)
            modules(appModule)
            modules(networkModule)
            modules(databaseModule)
        }
    }
}
