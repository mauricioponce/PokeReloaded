package cl.eme.pokemonesreloaded

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import cl.eme.pokemonesreloaded.databinding.ActivityMainBinding


/**
 * [X] Control de versiones
 * [X] view binding
 *      [X] activar (build.gradle app)
 *      [ ] actualizar Activities y fragments con viewBinding
 * [X] MainActivity
 *      [X] fragment container view
 * [X] consumo de API
 *      [X] permiso de internet
 *      [X] clearTextTraffic si es https
 *      [X] dependencias retrofit
 *      [X] pojos
 *      [X] interfaz de operaciones
 *      [X] cliente retrofit
 * [ ] ViewModel
 * [ ] Listado
 *      [ ] Fragmento de listado
 *      [ ] layout
 *      [ ] recycler view
 *      [ ] Viewholder
 *      [ ] adapter
 * [ ] detalle
 *      [ ] fragmento de detalle
 *      [ ] layout
 *      [ ] consumo de imágenes
 *
 * [ ] ROOM
 *      [ ] interfaz de operaciones (DAO)
 *      [ ] pojos (entities)
 *      [ ] cliente
 */

class MainActivity : AppCompatActivity() {

    val pokeVM : PokeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pokeVM.getPokemones()
    }
}