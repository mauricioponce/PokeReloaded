package cl.eme.pokemonesreloaded.listing

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cl.eme.pokemonesreloaded.data.Pokemon
import cl.eme.pokemonesreloaded.databinding.PokeItemBinding

class PokeAdapter: RecyclerView.Adapter<PokeVH>() {

    private var pokelist = listOf<Pokemon>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokeVH {
        val binding = PokeItemBinding.inflate(LayoutInflater.from(parent.context))

        return PokeVH(binding)
    }

    override fun onBindViewHolder(holder: PokeVH, position: Int) {
        val poke = pokelist[position]

        holder.bind(poke)
    }

    override fun getItemCount() = pokelist.size

    fun update(ppokelist: List<Pokemon>) {
        pokelist = ppokelist
        notifyDataSetChanged()
    }
}

class PokeVH(val binding: PokeItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(pokemon: Pokemon) {
        binding.textView.text = pokemon.name
    }
}
