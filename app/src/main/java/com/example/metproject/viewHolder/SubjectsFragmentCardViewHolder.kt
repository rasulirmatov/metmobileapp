package com.example.metproject.viewHolder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.metproject.models.SubjectsFragmentCardModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.subjects_fragment_card_item.view.*

class SubjectsFragmentCardViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

    val subject_image: ImageView = itemView.subject_image
    val subject_name: TextView = itemView.subject_name

    fun bind(subject: SubjectsFragmentCardModel) {
        Picasso.with(itemView.context)
            .load(subject.subject_image)
            .fit()
            .into(subject_image)
        subject_name.text = subject.subject_name
    }

}