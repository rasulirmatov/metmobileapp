package com.example.metproject.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.example.metproject.databinding.FragmentClassesBottomSheetDialogCardItemBinding
import com.example.metproject.databinding.FragmentClassesCardItemBinding
import com.example.metproject.databinding.FragmentOlympiadSubjectsCardItemBinding
import com.example.metproject.databinding.FragmentSubjectsBottomSheetDialogCardItemBinding
import com.example.metproject.models.response.Classes
import com.example.metproject.models.response.OlympiadSubject
import com.example.metproject.models.response.Subjects


class OlympiadSubjectsFragmentCardViewHolder(val binding: FragmentOlympiadSubjectsCardItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(data: OlympiadSubject) {
        binding.recyclerData = data
        binding.executePendingBindings()
    }

}