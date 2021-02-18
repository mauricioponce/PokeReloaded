package cl.eme.pokemonesreloaded.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import cl.eme.pokemonesreloaded.PokeViewModel
import cl.eme.pokemonesreloaded.databinding.FragmentDetailBinding
import coil.load

class DetailFragment: Fragment() {

    private lateinit var binding: FragmentDetailBinding

    private val pokeViewModel: PokeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater)


        val pokemon = pokeViewModel.getSelected()
        Log.d("DetailFragment", "$pokemon")


        pokeViewModel.consumeDetail(pokemon.id)

        pokeViewModel.getDetail().observe(viewLifecycleOwner, {
            binding.tvName.text = it.name
            binding.tvPokeId.text = it.id
            binding.ivPoke.load(it.img)
        })

        return binding.root
    }
}