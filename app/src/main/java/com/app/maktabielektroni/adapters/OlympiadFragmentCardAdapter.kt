package com.app.maktabielektroni.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.app.maktabielektroni.R
import com.app.maktabielektroni.models.response.OlympiadClass
import com.app.maktabielektroni.viewHolder.OlympiadFragmentCardViewHolder


class OlympiadFragmentCardAdapter : RecyclerView.Adapter<OlympiadFragmentCardViewHolder>() {

    var listClasses = mutableListOf<OlympiadClass>()

    fun setDataList(data: MutableList<OlympiadClass>) {
        this.listClasses = data
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OlympiadFragmentCardViewHolder {
        val view = LayoutInflater.from(parent.context)
        val binding = com.app.maktabielektroni.databinding.FragmentOlympiadCardItemBinding.inflate(
            view,
            parent,
            false
        )
        return OlympiadFragmentCardViewHolder(binding)
    }

    override fun getItemCount(): Int = listClasses.size

    override fun onBindViewHolder(holder: OlympiadFragmentCardViewHolder, position: Int) {

        holder.itemView.setOnClickListener {
            val bundle = bundleOf("class_id" to listClasses[position].sinfId.toString())
            bundle.putString("class_number", listClasses[position].sinf)
            Navigation.findNavController(it)
                .navigate(R.id.action_olympiad_to_olympiadSubjectsFragment, bundle)
        }

        holder.bind(listClasses[position])

    }

}
