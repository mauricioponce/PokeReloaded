package cl.eme.pokemonesreloaded.listing

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import cl.eme.pokemonesreloaded.data.pojo.Pokemon
import cl.eme.pokemonesreloaded.databinding.PokeItemBinding

class PokeAdapter: ListAdapter<Pokemon, PokeVH>(Pokemon.Companion.DiffCallback) {

    private var pokelist = mutableListOf<Pokemon>()

    private val selectedItem = MutableLiveData<Pokemon>()

    fun selectedItem(): LiveData<Pokemon> = selectedItem

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokeVH {
        val binding = PokeItemBinding.inflate(LayoutInflater.from(parent.context))

        return PokeVH(binding)
    }

    override fun onBindViewHolder(holder: PokeVH, position: Int) {
        Log.d("bb", "onBind")
        val poke = getItem(position)
        holder.bind(poke)
        holder.itemView.setOnClickListener {
            Log.d("PokeAdapter", "elemento seleccionado $poke")
            selectedItem.value = poke
        }
    }

    fun update(ppokelist: List<Pokemon>) {
        Log.d("aa", "update ${ppokelist.size}")
        pokelist = ppokelist.toMutableList()
        submitList(ppokelist)
    }

    fun remove() {
        pokelist.removeAt(5)
        update(pokelist)
    }
}

class PokeVH(val binding: PokeItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(pokemon: Pokemon) {
        binding.textView.text = pokemon.name
    }
}
/*
class PokeCallback() : DiffUtil.ItemCallback<Pokemon>() {
    override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
        return oldItem == newItem
    }
}
 */


