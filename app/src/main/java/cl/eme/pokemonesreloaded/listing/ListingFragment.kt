package cl.eme.pokemonesreloaded.listing

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import cl.eme.pokemonesreloaded.PokeViewModel
import cl.eme.pokemonesreloaded.databinding.FragmentListingBinding

class ListingFragment: Fragment() {

    private lateinit var binding: FragmentListingBinding

    private val pokeViewModel: PokeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListingBinding.inflate(inflater)


        val adapter = PokeAdapter()
        binding.pokeList.adapter = adapter
        // OJO, pestaña y ceja -> si no se ve la info en el recyclerview
        binding.pokeList.layoutManager = GridLayoutManager(context, 1)

        pokeViewModel.pokelist().observe(viewLifecycleOwner, {
            Log.d("ListingFragment", "actualizando info de pokemons ${it.size}")
            adapter.update(it)
        })



        return binding.root
    }
}