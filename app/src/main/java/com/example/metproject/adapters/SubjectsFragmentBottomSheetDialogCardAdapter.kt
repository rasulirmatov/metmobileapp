package com.example.metproject.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.metproject.R
import com.example.metproject.models.response.Classes
import com.example.metproject.models.response.ClassesItem
import com.example.metproject.models.response.Subjects
import com.example.metproject.utils.SuccessResponse
import com.example.metproject.viewHolder.ClassesFragmentBottomSheetDialogCardViewHolder
import com.example.metproject.viewHolder.ClassesFragmentCardViewHolder
import com.example.metproject.viewHolder.SubjectsFragmentBottomSheetDialogCardViewHolder


class SubjectsFragmentBottomSheetDialogCardAdapter(var itemClick: (subject_id: String) -> Unit) :
    RecyclerView.Adapter<SubjectsFragmentBottomSheetDialogCardViewHolder>() {


    var listSubjects = mutableListOf<Subjects>()

    fun setDataList(data: MutableList<Subjects>) {
        this.listSubjects = data
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SubjectsFragmentBottomSheetDialogCardViewHolder {
        val view = LayoutInflater.from(parent.context)
        val binding =
            com.example.metproject.databinding.FragmentSubjectsBottomSheetDialogCardItemBinding.inflate(
                view,
                parent,
                false
            )
        return SubjectsFragmentBottomSheetDialogCardViewHolder(binding)
    }

    override fun getItemCount(): Int = listSubjects.size

    override fun onBindViewHolder(
        holder: SubjectsFragmentBottomSheetDialogCardViewHolder,
        position: Int
    ) {

        holder.itemView.setOnClickListener {
            itemClick.invoke(listSubjects[position].id.toString())
        }

        holder.bind(listSubjects[position])

    }


}