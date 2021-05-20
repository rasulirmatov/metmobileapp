package com.app.maktabielektroni.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.app.maktabielektroni.databinding.FragmentOlympiadCardItemBinding
import com.app.maktabielektroni.models.response.OlympiadClass

class OlympiadFragmentCardViewHolder(val binding: FragmentOlympiadCardItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(data: OlympiadClass) {
        binding.recyclerData = data
        binding.executePendingBindings()
    }

}