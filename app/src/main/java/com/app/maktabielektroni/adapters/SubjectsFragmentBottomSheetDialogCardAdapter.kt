package com.app.maktabielektroni.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.maktabielektroni.models.response.Subjects
import com.app.maktabielektroni.viewHolder.SubjectsFragmentBottomSheetDialogCardViewHolder


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
            com.app.maktabielektroni.databinding.FragmentSubjectsBottomSheetDialogCardItemBinding.inflate(
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