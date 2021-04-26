package com.example.metproject.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.metproject.R
import com.example.metproject.models.SubjectsFragmentCardModel
import com.example.metproject.models.response.ClassesItem
import com.example.metproject.models.response.SubjectsItem
import com.example.metproject.viewHolder.ClassesFragmentCardViewHolder
import com.example.metproject.viewHolder.SubjectsFragmentCardViewHolder
import com.squareup.picasso.Picasso

class SubjectsFragmentCardAdapter : RecyclerView.Adapter<SubjectsFragmentCardViewHolder>() {

    var listSubjects = mutableListOf<SubjectsItem>()

    fun setDataList(data: MutableList<SubjectsItem>) {
        this.listSubjects = data
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SubjectsFragmentCardViewHolder {
        val view = LayoutInflater.from(parent.context)
        val binding = com.example.metproject.databinding.FragmentSubjectsCardItemBinding.inflate(
            view,
            parent,
            false
        )
        return SubjectsFragmentCardViewHolder(binding)
    }

    override fun getItemCount(): Int = listSubjects.size

    override fun onBindViewHolder(holder: SubjectsFragmentCardViewHolder, position: Int) {

        holder.itemView.setOnClickListener {
            val bundle = bundleOf("subject_id" to listSubjects[position].id.toString())
            Navigation.findNavController(it)
                .navigate(R.id.action_subjects_to_classesBottomSheetDialogFragment, bundle)
        }


        holder.bind(listSubjects[position])

    }

}