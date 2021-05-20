package com.app.maktabielektroni.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.app.maktabielektroni.databinding.FragmentClassesBottomSheetDialogCardItemBinding
import com.app.maktabielektroni.models.response.Classes


class ClassesFragmentBottomSheetDialogCardViewHolder(val binding: FragmentClassesBottomSheetDialogCardItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(data: Classes) {
        binding.recyclerData = data
        binding.executePendingBindings()
    }

}