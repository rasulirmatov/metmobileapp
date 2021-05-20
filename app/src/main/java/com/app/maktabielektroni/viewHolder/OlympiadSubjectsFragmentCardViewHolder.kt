package com.app.maktabielektroni.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.app.maktabielektroni.databinding.FragmentOlympiadSubjectsCardItemBinding
import com.app.maktabielektroni.models.response.OlympiadSubject


class OlympiadSubjectsFragmentCardViewHolder(val binding: FragmentOlympiadSubjectsCardItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(data: OlympiadSubject) {
        binding.recyclerData = data
        binding.executePendingBindings()
    }

}