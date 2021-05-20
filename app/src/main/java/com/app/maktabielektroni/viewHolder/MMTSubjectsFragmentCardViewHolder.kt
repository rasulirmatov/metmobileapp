package com.app.maktabielektroni.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.app.maktabielektroni.databinding.FragmentMMTSubjectCardItemBinding
import com.app.maktabielektroni.models.response.Mmt
import com.app.maktabielektroni.utils.Constants
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_m_m_t_subject_card_item.view.*


class MMTSubjectsFragmentCardViewHolder(val binding: FragmentMMTSubjectCardItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(data: Mmt) {
        binding.recyclerData = data
        Picasso.with(itemView.context)
            .load(Constants.image_path+data.subject.imageSrc)
            .centerCrop()
            .fit()
            .into(itemView.subject_image_subject)
        binding.executePendingBindings()
    }

}