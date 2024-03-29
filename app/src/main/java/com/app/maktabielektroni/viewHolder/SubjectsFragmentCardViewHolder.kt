package com.app.maktabielektroni.viewHolder

import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.RecyclerView
import com.app.maktabielektroni.databinding.FragmentSubjectsCardItemBinding
import com.app.maktabielektroni.models.response.SubjectsItem
import com.app.maktabielektroni.utils.Constants
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_subjects_card_item.view.*

class SubjectsFragmentCardViewHolder(val binding: FragmentSubjectsCardItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    private var vibrantSwatch: Palette.Swatch? = null
    private var lightVibrantSwatch: Palette.Swatch? = null
    private var darkVibrantSwatch: Palette.Swatch? = null
    private var mutedSwatch: Palette.Swatch? = null
    private var lightMutedSwatch: Palette.Swatch? = null
    private var darkMutedSwatch: Palette.Swatch? = null

    private var swatchNumber = 0

//    val subject_image: ImageView = itemView.subject_image_subject
//    val subject_name: TextView = itemView.subject_name

    fun bind(data: SubjectsItem) {

//        GlobalScope.launch(Dispatchers.IO){
//            val bitmap = BitmapFactory.decodeStream(URL(data.imageSrc).openStream())
//            Palette.from(bitmap).maximumColorCount(32).generate { palette ->
//                vibrantSwatch = palette!!.vibrantSwatch
//                lightVibrantSwatch = palette.lightVibrantSwatch
//                darkVibrantSwatch = palette.darkVibrantSwatch
//                mutedSwatch = palette.mutedSwatch
//                lightMutedSwatch = palette.lightMutedSwatch
//                darkMutedSwatch = palette.darkMutedSwatch
//
//                itemView.main_fragment_card_view.setCardBackgroundColor(mutedSwatch!!.rgb)
//                itemView.subject_name.setTextColor(mutedSwatch!!.titleTextColor)
//            }
//        }

        binding.recyclerData = data
        Picasso.with(itemView.context)
            .load(Constants.image_path+data.imageSrc)
            .centerCrop()
            .fit()
            .into(itemView.subject_image_subject)

        binding.executePendingBindings()




//        subject_name.text = subject.subject_name
    }
}