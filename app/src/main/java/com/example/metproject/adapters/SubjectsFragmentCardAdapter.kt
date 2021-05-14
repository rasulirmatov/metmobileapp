package com.example.metproject.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.metproject.R
import com.example.metproject.models.SubjectsFragmentCardModel
import com.example.metproject.models.response.ClassesItem
import com.example.metproject.models.response.SubjectsItem
import com.example.metproject.viewHolder.ClassesFragmentCardViewHolder
import com.example.metproject.viewHolder.SubjectsFragmentCardViewHolder
import com.squareup.picasso.Picasso
import java.util.*
import kotlin.collections.ArrayList

class SubjectsFragmentCardAdapter (private var subjectsList: MutableList<SubjectsItem>) : RecyclerView.Adapter<SubjectsFragmentCardViewHolder>(),
    Filterable {

    var listSubjectsFilter = mutableListOf<SubjectsItem>()

    init {
        listSubjectsFilter = subjectsList
    }



    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SubjectsFragmentCardViewHolder {
        val view = LayoutInflater.from(parent.context)
        val binding = com.example.metproject.databinding.FragmentSubjectsCardItemBinding.inflate(
            view,
            parent,
            false
        )
        return SubjectsFragmentCardViewHolder(binding)
    }

    override fun getItemCount(): Int = listSubjectsFilter.size

    override fun onBindViewHolder(holder: SubjectsFragmentCardViewHolder, position: Int) {

        holder.itemView.setOnClickListener {
            val bundle = bundleOf("subject_id" to listSubjectsFilter[position].id.toString())
            Navigation.findNavController(it)
                .navigate(R.id.action_subjects_to_classesBottomSheetDialogFragment, bundle)
        }


        holder.bind(listSubjectsFilter[position])

    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    listSubjectsFilter = subjectsList
                } else {
                    val resultList = mutableListOf<SubjectsItem>()
                    for (row in subjectsList) {
                        if (row.name.toLowerCase(Locale.ROOT)
                                .contains(charSearch.toLowerCase(Locale.ROOT))
                        ) {
                            resultList.add(row)
                        }
                    }
                    listSubjectsFilter = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = listSubjectsFilter
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                listSubjectsFilter = results?.values as MutableList<SubjectsItem>
                notifyDataSetChanged()
            }
        }
    }

}