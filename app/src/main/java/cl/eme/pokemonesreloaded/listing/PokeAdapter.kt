package cl.eme.pokemonesreloaded.listing

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import cl.eme.pokemonesreloaded.data.Pokemon
import cl.eme.pokemonesreloaded.databinding.PokeItemBinding

class PokeAdapter: RecyclerView.Adapter<PokeVH>() {

    private var pokelist = listOf<Pokemon>()

    private val selectedItem = MutableLiveData<Pokemon>()

    fun selectedItem(): LiveData<Pokemon> = selectedItem

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokeVH {
        val binding = PokeItemBinding.inflate(LayoutInflater.from(parent.context))

        return PokeVH(binding)
    }

    override fun onBindViewHolder(holder: PokeVH, position: Int) {
        val poke = pokelist[position]
        holder.bind(poke)
        holder.itemView.setOnClickListener {
            Log.d("PokeAdapter", "elemento seleccionado $poke")
            selectedItem.value = poke
        }
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
