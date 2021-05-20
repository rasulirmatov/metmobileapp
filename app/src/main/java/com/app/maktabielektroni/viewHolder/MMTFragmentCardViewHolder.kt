package com.app.maktabielektroni.viewHolder

import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.app.maktabielektroni.R
import com.app.maktabielektroni.databinding.FragmentMmtCardItemBinding
import com.app.maktabielektroni.databinding.FragmentOlympiadCardItemBinding
import com.app.maktabielektroni.models.response.ClusterItem
import com.app.maktabielektroni.models.response.OlympiadClass
import kotlinx.android.synthetic.main.fragment_m_m_t_subject_card_item.view.*

class MMTFragmentCardViewHolder(val binding: FragmentMmtCardItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(data: ClusterItem) {
        binding.recyclerData = data
        binding.executePendingBindings()
    }

}