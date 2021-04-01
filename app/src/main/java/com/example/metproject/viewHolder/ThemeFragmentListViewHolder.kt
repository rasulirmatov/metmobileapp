package com.example.metproject.viewHolder

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.metproject.models.ClassesFragmentCardModel
import com.example.metproject.models.SubjectsFragmentCardModel
import com.example.metproject.models.ThemeFragmentListModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_classes_card_item.view.*
import kotlinx.android.synthetic.main.fragment_subjects_card_item.view.*
import kotlinx.android.synthetic.main.fragment_theme_list_item.view.*

class ThemeFragmentListViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

    val theme_name: TextView = itemView.theme_name_text_view
    val theme_number: TextView = itemView.theme_number_text_view
    val theme_descritpion: TextView = itemView.theme_description_text_view
    val theme_btn: Button = itemView.btn_detail
    val theme_expand_arrow: ImageView = itemView.expand_arrow
    val theme_card: CardView = itemView.theme_fragment_list_item_card_view

    fun bind(theme: ThemeFragmentListModel) {
        theme_name.text = theme.theme_name
        theme_number.text = theme.theme_number
        theme_descritpion.text = theme.theme_description
    }

}