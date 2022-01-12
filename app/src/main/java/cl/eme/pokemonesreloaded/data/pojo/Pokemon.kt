package cl.eme.pokemonesreloaded.data.pojo

import androidx.recyclerview.widget.DiffUtil


data class Pokemon(val id: String, val img: String, val name: String) {
    companion object {
        object DiffCallback: DiffUtil.ItemCallback<Pokemon>() {
            override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
                return oldItem == newItem
            }
        }
    }
}