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
import com.example.metproject.models.response.OlympiadSubject
import com.example.metproject.models.response.Subjects
import com.example.metproject.utils.SuccessResponse
import com.example.metproject.viewHolder.ClassesFragmentBottomSheetDialogCardViewHolder
import com.example.metproject.viewHolder.ClassesFragmentCardViewHolder
import com.example.metproject.viewHolder.OlympiadSubjectsFragmentCardViewHolder
import com.example.metproject.viewHolder.SubjectsFragmentBottomSheetDialogCardViewHolder


class OlympiadSubjectsFragmentAdapter(var itemClick: (subject_id: String,subject_name:String) -> Unit) :
    RecyclerView.Adapter<OlympiadSubjectsFragmentCardViewHolder>() {


    var listSubjects = mutableListOf<OlympiadSubject>()

    fun setDataList(data: MutableList<OlympiadSubject>) {
        this.listSubjects = data
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OlympiadSubjectsFragmentCardViewHolder {
        val view = LayoutInflater.from(parent.context)
        val binding =
            com.example.metproject.databinding.FragmentOlympiadSubjectsCardItemBinding.inflate(
                view,
                parent,
                false
            )
        return OlympiadSubjectsFragmentCardViewHolder(binding)
    }

    override fun getItemCount(): Int = listSubjects.size

    override fun onBindViewHolder(
        holder: OlympiadSubjectsFragmentCardViewHolder,
        position: Int
    ) {

        holder.itemView.setOnClickListener {
            itemClick.invoke(listSubjects[position].subjectId.toString(),listSubjects[position].subjectName)
        }

        holder.bind(listSubjects[position])

    }


}