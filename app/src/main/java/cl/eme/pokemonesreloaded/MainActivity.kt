package cl.eme.pokemonesreloaded

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cl.eme.pokemonesreloaded.databinding.ActivityMainBinding


/**
 * [X] Control de versiones
 * [X] view binding
 *      [X] activar (build.gradle app)
 *      [ ] actualizar Activities y fragments con viewBinding
 * [X] MainActivity
 *      [X] fragment container view
 * [ ] consumo de API
 *      [ ] permiso de internet
 *      [ ] dependencias retrofit
 *      [ ] pojos
 *      [ ] interfaz de operaciones
 *      [ ] cliente retrofit
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}