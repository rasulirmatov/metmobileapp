package com.example.metproject.adapters

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.metproject.R
import com.example.metproject.models.SliderModel
import com.example.metproject.models.response.Slide
import com.example.metproject.utils.Constants
import com.smarteist.autoimageslider.SliderViewAdapter


class SliderAdapter(private val context: Context) :
    SliderViewAdapter<SliderAdapter.SliderAdapterVH>() {
    private var mSliderItems: MutableList<Slide> = ArrayList()

    fun setSliderItems(sliderItems: MutableList<Slide>) {
        mSliderItems = sliderItems
        notifyDataSetChanged()
    }

    fun deleteItem(position: Int) {
        mSliderItems.removeAt(position)
        notifyDataSetChanged()
    }

    fun addItem(sliderItem: Slide) {
        mSliderItems.add(sliderItem)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup): SliderAdapterVH {
        val inflate: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.image_slider_layout_item, null)
        return SliderAdapterVH(inflate)
    }

    override fun onBindViewHolder(
        viewHolder: SliderAdapterVH,
        position: Int
    ) {
        val sliderItem: Slide = mSliderItems[position]
        viewHolder.textViewTitle.setText(sliderItem.title)
        viewHolder.textViewDescription.setText(sliderItem.description)
        val imageBytes = Base64.decode(sliderItem.imgSrc.replace("data:image/jpeg;base64,",""), Base64.DEFAULT)
        val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        Log.i("BitMapimage",sliderItem.imgSrc)

        Glide.with(viewHolder.itemView)
            .load(decodedImage)
            .placeholder(R.drawable.ic_place_holder)
            .fitCenter()
            .into(viewHolder.imageViewBackground)

        viewHolder.itemView1.setOnClickListener(View.OnClickListener {
            context?.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(Constants.slider_url+sliderItem.url)))
        })

    }

    override fun getCount(): Int {
        //slider view count could be dynamic size
        return mSliderItems.size
    }

    inner class SliderAdapterVH(itemView: View) : ViewHolder(itemView) {
        var itemView1: View
        var imageViewBackground: ImageView
        var imageGifContainer: ImageView
        var textViewTitle: TextView
        var textViewDescription: TextView

        init {
            imageViewBackground =
                itemView.findViewById(R.id.iv_auto_image_slider)
            imageGifContainer =
                itemView.findViewById(R.id.iv_gif_container)
            textViewTitle = itemView.findViewById(R.id.slider_title)
            textViewDescription = itemView.findViewById(R.id.slider_description)
            this.itemView1 = itemView
        }
    }

}