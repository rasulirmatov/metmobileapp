package com.example.metproject.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.metproject.R
import com.example.metproject.models.MainFragmentRecomendationModel
import com.example.metproject.viewHolder.MainFragmentRecomendationViewHolder

class MainFragmentRecomendationAdapter(list: MutableList<MainFragmentRecomendationModel>) :
    RecyclerView.Adapter<MainFragmentRecomendationViewHolder>() {

    private var listSubjects = list

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MainFragmentRecomendationViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_main_recomendations_item, parent, false)
        return MainFragmentRecomendationViewHolder(view)
    }

    override fun getItemCount(): Int = listSubjects.size

    override fun onBindViewHolder(holder: MainFragmentRecomendationViewHolder, position: Int) {

        holder.itemView.setOnClickListener {

            Toast.makeText(
                holder.subject_name.context,
                listSubjects[position].subject_id.toString(),
                Toast.LENGTH_SHORT
            ).show()

            Log.d("itemData", listSubjects[position].subject_id.toString())
//            holder.nextSwatch()

//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

        }

        holder.bind(listSubjects[position])
//        holder.nextSwatch()

    }

//    fun set(list: MutableList<MainFragmentRecomendationModel>) {
//        this.listSubjects.clear()
//        this.listSubjects.addAll(list)
//        notifyDataSetChanged()
//    }

}