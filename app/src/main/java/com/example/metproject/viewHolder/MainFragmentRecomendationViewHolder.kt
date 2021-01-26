package com.example.metproject.viewHolder

import android.content.Context
import android.content.res.Resources
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.palette.graphics.Palette
import androidx.palette.graphics.Palette.Swatch
import androidx.recyclerview.widget.RecyclerView
import com.example.metproject.R
import com.example.metproject.models.MainFragmentRecomendationModel
import com.example.metproject.views.FadingImageView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.subjects_fragment_card_item.view.*


class MainFragmentRecomendationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val subject_image: ImageView = itemView.subject_image
    val subject_name: TextView = itemView.subject_name
    val cardView: CardView = itemView.main_fragment_card_view

//    val select_btn: Button = itemView.select_btn

    private var vibrantSwatch: Swatch? = null
    private var lightVibrantSwatch: Swatch? = null
    private var darkVibrantSwatch: Swatch? = null
    private var mutedSwatch: Swatch? = null
    private var lightMutedSwatch: Swatch? = null
    private var darkMutedSwatch: Swatch? = null

    private var swatchNumber = 0

    fun bind(subject: MainFragmentRecomendationModel) {
        Picasso.with(itemView.context)
            .load(subject.subject_image)
            .fit()
            .into(subject_image)
        subject_name.text = subject.subject_name


//        val bitmap = (in as BitmapDrawable).bitmap
        val bitmap = BitmapFactory.decodeResource(itemView.context.resources, subject.subject_image)

        Palette.from(bitmap).maximumColorCount(32).generate { palette ->
            vibrantSwatch = palette!!.vibrantSwatch
            lightVibrantSwatch = palette.lightVibrantSwatch
            darkVibrantSwatch = palette.darkVibrantSwatch
            mutedSwatch = palette.mutedSwatch
            lightMutedSwatch = palette.lightMutedSwatch
            darkMutedSwatch = palette.darkMutedSwatch

            cardView.setCardBackgroundColor(mutedSwatch!!.rgb)
            subject_name.setTextColor(mutedSwatch!!.titleTextColor)
        }
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
            subject_name.setTextColor(currentSwatch.titleTextColor)
        } else {
            subject_name.setTextColor(Color.WHITE)
            cardView.setCardBackgroundColor(ContextCompat.getColor(itemView.context,R.color.primary))
        }
        if (swatchNumber < 5) {
            swatchNumber++
        } else {
            swatchNumber = 0
        }

    }

}