package com.app.maktabielektroni.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.maktabielektroni.models.response.Classes
import com.app.maktabielektroni.viewHolder.ClassesFragmentBottomSheetDialogCardViewHolder

class ClassesFragmentBottomSheetDialogCardAdapter(var itemClick: (class_id: String) -> Unit) :
    RecyclerView.Adapter<ClassesFragmentBottomSheetDialogCardViewHolder>() {

    var listClasses = mutableListOf<Classes>()

    fun setDataList(data: MutableList<Classes>) {
        this.listClasses = data
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ClassesFragmentBottomSheetDialogCardViewHolder {
        val view = LayoutInflater.from(parent.context)
        val binding =
            com.app.maktabielektroni.databinding.FragmentClassesBottomSheetDialogCardItemBinding.inflate(
                view,
                parent,
                false
            )
        return ClassesFragmentBottomSheetDialogCardViewHolder(binding)
    }

    override fun getItemCount(): Int = listClasses.size

    override fun onBindViewHolder(
        holder: ClassesFragmentBottomSheetDialogCardViewHolder,
        position: Int
    ) {

        holder.itemView.setOnClickListener {
            itemClick.invoke(listClasses[position].id.toString())
        }


        holder.bind(listClasses[position])

    }


}