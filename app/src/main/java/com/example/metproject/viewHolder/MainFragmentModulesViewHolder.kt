package com.example.metproject.viewHolder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.metproject.models.MainFragmentModulesModel
import com.example.metproject.models.SubjectsFragmentCardModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_main_recomendations_item.view.*
//import kotlinx.android.synthetic.main.fragment_subjects_card_item.view.*
import kotlinx.android.synthetic.main.fragment_subjects_card_item.view.subject_name

class MainFragmentModulesViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

    val module_image: ImageView = itemView.subject_image
    val module_name: TextView = itemView.subject_name

    fun bind(module: MainFragmentModulesModel) {
        Picasso.with(itemView.context)
            .load(module.module_image)
            .fit()
            .into(module_image)
        module_name.text = module.module_name    }

}