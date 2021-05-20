package com.app.maktabielektroni.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.app.maktabielektroni.databinding.FragmentThemeListItemBinding
//import com.example.metproject.models.ClassesFragmentCardModel
import com.app.maktabielektroni.models.response.Themes

class ThemeFragmentListViewHolder(val binding: FragmentThemeListItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    //    val theme_name: TextView = itemView.theme_name_text_view
//    val theme_number: TextView = itemView.theme_number_text_view
//    val theme_descritpion: TextView = itemView.theme_description_text_view
//    val theme_btn: Button = itemView.btn_detail
//    val theme_expand_arrow: ImageView = itemView.expand_arrow
//    val theme_card: CardView = itemView.theme_fragment_list_item_card_view
//
//    fun bind(theme: ThemeFragmentListModel) {
//        theme_name.text = theme.theme_name
//        theme_number.text = theme.theme_number
//        theme_descritpion.text = theme.theme_description
//    }
    fun bind(data: Themes) {
        binding.recyclerData = data
        binding.executePendingBindings()
    }

}