package com.app.maktabielektroni.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.maktabielektroni.models.response.OlympiadSubject
import com.app.maktabielektroni.viewHolder.OlympiadSubjectsFragmentCardViewHolder


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
            com.app.maktabielektroni.databinding.FragmentOlympiadSubjectsCardItemBinding.inflate(
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