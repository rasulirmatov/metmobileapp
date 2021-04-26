package com.example.metproject.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.metproject.R
import com.example.metproject.models.response.ClassesItem
import com.example.metproject.viewHolder.ClassesFragmentCardViewHolder


class ClassesFragmentCardAdapter : RecyclerView.Adapter<ClassesFragmentCardViewHolder>() {

    var listClasses = mutableListOf<ClassesItem>()

    fun setDataList(data: MutableList<ClassesItem>) {
        this.listClasses = data
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ClassesFragmentCardViewHolder {
        val view = LayoutInflater.from(parent.context)
        val binding = com.example.metproject.databinding.FragmentClassesCardItemBinding.inflate(
            view,
            parent,
            false
        )
        return ClassesFragmentCardViewHolder(binding)
    }

    override fun getItemCount(): Int = listClasses.size

    override fun onBindViewHolder(holder: ClassesFragmentCardViewHolder, position: Int) {

        holder.itemView.setOnClickListener {
            val bundle = bundleOf("class_id" to listClasses[position].id.toString())
            Navigation.findNavController(it)
                .navigate(R.id.action_classes_to_subjectsBottomSheetDialogFragment, bundle)
        }

        holder.bind(listClasses[position])

    }

}
