package com.example.metproject.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.metproject.R
import com.example.metproject.models.ClassesFragmentCardModel
//import com.example.metproject.models.SubjectsFragmentCardModel
import com.example.metproject.viewHolder.ClassesFragmentCardViewHolder
//import com.example.metproject.viewHolder.SubjectsFragmentCardViewHolder

var text_send = "text"

class ClassesFragmentBottomSheetDialogCardAdapter(val parentFragment: Fragment) : RecyclerView.Adapter<ClassesFragmentCardViewHolder>() {

    private val listClasses = mutableListOf<ClassesFragmentCardModel>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ClassesFragmentCardViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_classes_card_item, parent, false)
        return ClassesFragmentCardViewHolder(view)
    }

    override fun getItemCount(): Int = listClasses.size

    override fun onBindViewHolder(holder: ClassesFragmentCardViewHolder, position: Int) {

        holder.itemView.setOnClickListener {

            Toast.makeText(
                holder.class_number.context,
                listClasses[position].class_id.toString(),
                Toast.LENGTH_SHORT
            ).show()

            text_send = "Rasul"

            Log.d("itemData" ,listClasses[position].class_id.toString())

            parentFragment.findNavController().navigate(R.id.action_classesBottomSheetDialogFragment_to_themeFragmentList)

        }

        holder.bind(listClasses[position])
    }

    fun set(list: MutableList<ClassesFragmentCardModel>) {
        this.listClasses.clear()
        this.listClasses.addAll(list)
        notifyDataSetChanged()
    }

}