package com.example.metproject.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.metproject.R
import com.example.metproject.models.SubjectsFragmentCardModel
import com.example.metproject.viewHolder.SubjectsFragmentCardViewHolder
import kotlin.coroutines.coroutineContext
import kotlin.math.log

class SubjectsFragmentCardAdapter : RecyclerView.Adapter<SubjectsFragmentCardViewHolder>() {

    private val listSubjects = mutableListOf<SubjectsFragmentCardModel>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SubjectsFragmentCardViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.subjects_fragment_card_item, parent, false)
        return SubjectsFragmentCardViewHolder(view)
    }

    override fun getItemCount(): Int = listSubjects.size

    override fun onBindViewHolder(holder: SubjectsFragmentCardViewHolder, position: Int) {

        holder.itemView.setOnClickListener {

            Toast.makeText(
                holder.subject_name.context,
                listSubjects[position].subject_id.toString(),
                Toast.LENGTH_SHORT
            ).show()


            Log.d("itemData" ,listSubjects[position].subject_id.toString())

//            Navigation.findNavController(it).navigate(R.id.signInFragment)

        }

        holder.bind(listSubjects[position])
    }

    fun set(list: MutableList<SubjectsFragmentCardModel>) {
        this.listSubjects.clear()
        this.listSubjects.addAll(list)
        notifyDataSetChanged()
    }

}