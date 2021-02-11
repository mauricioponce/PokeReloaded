package cl.eme.pokemonesreloaded

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle


/**
 * [ ] Control de versiones
 * [ ] view binding
 *      [ ] activar
 *      [ ] actualizar Activities y fragments con viewBinding
 * [ ] MainActivity
 *      [ ] fragment container view
 * [ ] ViewModel
 * [ ] Listado
 *      [ ] Fragmento de listado
 *      [ ] layout
 *      [ ] recycler view
 *      [ ] Viewholder
 *      [ ] adapter
 * [ ] consumo de API
 *     [ ] dependencias retrofit
 *     [ ] pojos
 *     [ ] interfaz de operaciones
 *     [ ] cliente retrofit
 *
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
        setContentView(R.layout.activity_main)
    }
}