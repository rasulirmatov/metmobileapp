package com.example.metproject.viewHolder

import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.palette.graphics.Palette.Swatch
import androidx.recyclerview.widget.RecyclerView
import com.example.metproject.R
import com.example.metproject.models.response.NewsItem
import com.example.metproject.utils.Constants
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_main_news_item.view.*
import kotlinx.android.synthetic.main.fragment_subjects_card_item.view.main_fragment_card_view


class MainFragmentNewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val news_image: ImageView = itemView.news_image
    val news_name: TextView = itemView.news_label
    val news_description: TextView = itemView.news_body
    val cardView: CardView = itemView.main_fragment_card_view
    val date: TextView = itemView.date

//    val select_btn: Button = itemView.select_btn

    private var vibrantSwatch: Swatch? = null
    private var lightVibrantSwatch: Swatch? = null
    private var darkVibrantSwatch: Swatch? = null
    private var mutedSwatch: Swatch? = null
    private var lightMutedSwatch: Swatch? = null
    private var darkMutedSwatch: Swatch? = null

    private var swatchNumber = 0

    fun bind(newsItem: NewsItem) {
//        itemView.main_fragment_card_view.visibility = View.GONE
//        GlobalScope.launch(Dispatchers.Default) {
//            val bitmap = BitmapFactory.decodeStream(URL(Constants.image_path+newsItem.imgSrc).openStream())
//            Palette.from(bitmap).maximumColorCount(32).generate { palette ->
//                vibrantSwatch = palette!!.vibrantSwatch
//                lightVibrantSwatch = palette.lightVibrantSwatch
//                darkVibrantSwatch = palette.darkVibrantSwatch
//                mutedSwatch = palette.mutedSwatch
//                lightMutedSwatch = palette.lightMutedSwatch
//                darkMutedSwatch = palette.darkMutedSwatch
//
////                itemView.main_fragment_card_view.setCardBackgroundColor(mutedSwatch!!.rgb)
////                itemView.news_label.setTextColor(mutedSwatch!!.titleTextColor)
////                itemView.news_body.setTextColor(mutedSwatch!!.titleTextColor)
//                itemView.main_fragment_card_view.visibility = View.VISIBLE
//            }
//        }

        Picasso.with(itemView.context)
            .load(Constants.image_path + newsItem.imgSrc)
            .placeholder(R.drawable.ic_place_holder)
            .into(news_image)
        news_name.text = newsItem.title
        news_description.text = newsItem.description

//                Toast.makeText(mCtx,transactionDetails.getTxnDate(),Toast.LENGTH_LONG).show();
        val time: List<String> = newsItem.updatedAt.replace("00000Z", " ").split("T")
        date.text = (time[0] + " " + time[1].replace(".0", ""))

    }

    fun nextSwatch() {
        var currentSwatch: Swatch? = null

        when (swatchNumber) {
            0 -> {
                currentSwatch = vibrantSwatch
            }
            1 -> {
                currentSwatch = lightVibrantSwatch
            }
            2 -> {
                currentSwatch = darkVibrantSwatch
            }
            3 -> {
                currentSwatch = mutedSwatch
            }
            4 -> {
                currentSwatch = lightMutedSwatch
            }
            5 -> {
                currentSwatch = darkMutedSwatch
            }
        }

        if (currentSwatch != null) {
//            rootLayout.setBackgroundColor(currentSwatch.rgb)
            cardView.setCardBackgroundColor(currentSwatch!!.rgb)
            news_name.setTextColor(currentSwatch.titleTextColor)
            news_description.setTextColor(currentSwatch.titleTextColor)
        } else {
            news_name.setTextColor(Color.WHITE)
            news_description.setTextColor(Color.WHITE)
            cardView.setCardBackgroundColor(
                ContextCompat.getColor(
                    itemView.context,
                    R.color.primary
                )
            )
        }
        if (swatchNumber < 5) {
            swatchNumber++
        } else {
            swatchNumber = 0
        }

    }

}