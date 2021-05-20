package com.app.maktabielektroni.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.app.maktabielektroni.databinding.FragmentClassesCardItemBinding
import com.app.maktabielektroni.models.response.ClassesItem

class ClassesFragmentCardViewHolder(val binding: FragmentClassesCardItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(data: ClassesItem) {
        binding.recyclerData = data
        binding.executePendingBindings()
    }

}