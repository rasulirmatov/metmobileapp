package com.example.metproject.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.example.metproject.databinding.FragmentClassesCardItemBinding
import com.example.metproject.models.response.ClassesItem

class ClassesFragmentCardViewHolder(val binding: FragmentClassesCardItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(data: ClassesItem) {
        binding.recyclerData = data
        binding.executePendingBindings()
    }

}