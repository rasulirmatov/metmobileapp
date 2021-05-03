package com.example.metproject.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.example.metproject.databinding.FragmentClassesBottomSheetDialogCardItemBinding
import com.example.metproject.databinding.FragmentClassesCardItemBinding
import com.example.metproject.models.response.Classes


class ClassesFragmentBottomSheetDialogCardViewHolder(val binding: FragmentClassesBottomSheetDialogCardItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(data: Classes) {
        binding.recyclerData = data
        binding.executePendingBindings()
    }

}