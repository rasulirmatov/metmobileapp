package com.example.metproject.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.example.metproject.databinding.FragmentClassesCardItemBinding
import com.example.metproject.databinding.FragmentOlympiadCardItemBinding
import com.example.metproject.models.response.ClassesItem
import com.example.metproject.models.response.OlympiadClass

class OlympiadFragmentCardViewHolder(val binding: FragmentOlympiadCardItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(data: OlympiadClass) {
        binding.recyclerData = data
        binding.executePendingBindings()
    }

}