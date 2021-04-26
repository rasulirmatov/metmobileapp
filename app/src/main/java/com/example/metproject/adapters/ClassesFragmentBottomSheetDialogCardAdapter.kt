package com.example.metproject.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.metproject.R
import com.example.metproject.models.response.Classes
import com.example.metproject.utils.SuccessResponse
import com.example.metproject.viewHolder.ClassesFragmentBottomSheetDialogCardViewHolder

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
            com.example.metproject.databinding.FragmentClassesBottomSheetDialogCardItemBinding.inflate(
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