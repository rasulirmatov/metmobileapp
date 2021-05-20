package com.app.maktabielektroni.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.maktabielektroni.models.response.Mmt
import com.app.maktabielektroni.viewHolder.MMTSubjectsFragmentCardViewHolder


class MMTSubjectsFragmentAdapter(var itemClick: (mmt_fan_id: String,component_name:String) -> Unit) :
    RecyclerView.Adapter<MMTSubjectsFragmentCardViewHolder>() {


    var listSubjects = mutableListOf<Mmt>()

    fun setDataList(data: MutableList<Mmt>) {
        this.listSubjects = data
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MMTSubjectsFragmentCardViewHolder {
        val view = LayoutInflater.from(parent.context)
        val binding =
            com.app.maktabielektroni.databinding.FragmentMMTSubjectCardItemBinding.inflate(
                view,
                parent,
                false
            )
        return MMTSubjectsFragmentCardViewHolder(binding)
    }

    override fun getItemCount(): Int = listSubjects.size

    override fun onBindViewHolder(
        holder: MMTSubjectsFragmentCardViewHolder,
        position: Int
    ) {

        holder.itemView.setOnClickListener {
            itemClick.invoke(listSubjects[position].mmtFanId.toString(),"Кластери " + listSubjects[position].clusterId+" | Фанни: "+listSubjects[position].subject.name+" | Компоненти: "+listSubjects[position].component)
        }


        holder.bind(listSubjects[position])

    }


}