package com.example.metproject.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.metproject.R
import com.example.metproject.models.HomeSliderModel


class HomeSliderAdapter(private  val homeSlides: List<HomeSliderModel>)
    :RecyclerView.Adapter<HomeSliderAdapter.HomeSliderViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeSliderViewHolder {
        return  HomeSliderViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.home_slider_item,
                parent,false
            )
        )
    }

    override fun getItemCount(): Int {
        return homeSlides.size
    }

    override fun onBindViewHolder(holder: HomeSliderViewHolder, position: Int) {
        holder.bind(homeSlides[position])
    }

    inner class HomeSliderViewHolder(view: View) : RecyclerView.ViewHolder(view){

        private val textTitle = view.findViewById<TextView>(R.id.slider_header_text)
        private val textDescription = view.findViewById<TextView>(R.id.slider_body_text)
        private val ImageIcon = view.findViewById<ImageView>(R.id.slider_image)

        fun bind(homeSlide: HomeSliderModel){
            textTitle.text = homeSlide.title
            textDescription.text = homeSlide.description
            ImageIcon.setImageResource(homeSlide.icon)
        }
    }
}