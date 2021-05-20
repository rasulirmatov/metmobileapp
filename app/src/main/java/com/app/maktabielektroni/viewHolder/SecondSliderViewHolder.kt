package com.app.maktabielektroni.viewHolder

import android.graphics.BitmapFactory
import android.graphics.Color
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.app.maktabielektroni.models.response.SlideItem
import kotlinx.android.synthetic.main.fragment_main_mudules_item.view.module_image
import kotlinx.android.synthetic.main.fragment_main_mudules_item.view.module_text
import kotlinx.android.synthetic.main.fragment_main_second_slider.view.*


class SecondSliderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val module_image: ImageView = itemView.module_image
    val module_name: TextView = itemView.module_text

    fun bind(sliderItem: SlideItem) {

        Log.i("TestingApi", sliderItem.bgColor)

        val imageBytes =
            Base64.decode(sliderItem.imgSrc.replace("data:image/png;base64,", ""), Base64.DEFAULT)
        val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)

        Glide.with(itemView.context)
            .load(decodedImage)
            .fitCenter()
            .into(module_image)
        module_name.text = sliderItem.title
//        itemView.main_fragment_card_view.setCardBackgroundColor(Color.parseColor(sliderItem.bgColor))
        itemView.card_view_component.setCardBackgroundColor(Color.parseColor(sliderItem.bgColor))
    }
}