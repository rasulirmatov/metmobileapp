package com.app.maktabielektroni.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.app.maktabielektroni.databinding.FragmentSubjectsBottomSheetDialogCardItemBinding
import com.app.maktabielektroni.models.response.Subjects


class SubjectsFragmentBottomSheetDialogCardViewHolder(val binding: FragmentSubjectsBottomSheetDialogCardItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(data: Subjects) {
        binding.recyclerData = data
        binding.executePendingBindings()
    }

}