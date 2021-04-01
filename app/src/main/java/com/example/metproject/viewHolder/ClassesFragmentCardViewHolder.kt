package com.example.metproject.viewHolder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.metproject.models.ClassesFragmentCardModel
import kotlinx.android.synthetic.main.fragment_classes_card_item.view.*

class ClassesFragmentCardViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

    val class_number: TextView = itemView.class_number

    fun bind(classes: ClassesFragmentCardModel) {
        class_number.text = classes.class_number
    }

}