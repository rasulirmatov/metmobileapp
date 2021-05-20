package com.app.maktabielektroni.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.app.maktabielektroni.R
import com.app.maktabielektroni.models.response.ClassesItem
import com.app.maktabielektroni.viewHolder.ClassesFragmentCardViewHolder


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
        val binding = com.app.maktabielektroni.databinding.FragmentClassesCardItemBinding.inflate(
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
