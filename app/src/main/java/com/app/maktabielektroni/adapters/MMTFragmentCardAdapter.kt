package com.app.maktabielektroni.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.app.maktabielektroni.R
import com.app.maktabielektroni.models.response.ClusterItem
import com.app.maktabielektroni.models.response.OlympiadClass
import com.app.maktabielektroni.viewHolder.MMTFragmentCardViewHolder
import com.app.maktabielektroni.viewHolder.OlympiadFragmentCardViewHolder


class MMTFragmentCardAdapter : RecyclerView.Adapter<MMTFragmentCardViewHolder>() {

    var listClusters = mutableListOf<ClusterItem>()

    fun setDataList(data: MutableList<ClusterItem>) {
        this.listClusters = data
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MMTFragmentCardViewHolder {
        val view = LayoutInflater.from(parent.context)
        val binding = com.app.maktabielektroni.databinding.FragmentMmtCardItemBinding.inflate(
            view,
            parent,
            false
        )
        return MMTFragmentCardViewHolder(binding)
    }

    override fun getItemCount(): Int = listClusters.size

    override fun onBindViewHolder(holder: MMTFragmentCardViewHolder, position: Int) {

        holder.itemView.setOnClickListener {
            val bundle = bundleOf("cluster_id" to listClusters[position].id.toString())
            bundle.putString("cluster_number", listClusters[position].name)
            Navigation.findNavController(it)
                .navigate(R.id.action_MMTFragment_to_MMTSubjectListFragment, bundle)
        }

        holder.bind(listClusters[position])

    }

}
