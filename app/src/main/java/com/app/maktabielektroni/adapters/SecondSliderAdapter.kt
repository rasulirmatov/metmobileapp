package com.app.maktabielektroni.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.app.maktabielektroni.R
import com.app.maktabielektroni.models.response.SlideItem
import com.app.maktabielektroni.utils.Constants
import com.app.maktabielektroni.viewHolder.SecondSliderViewHolder

class SecondSliderAdapter(list: MutableList<SlideItem>) :
    RecyclerView.Adapter<SecondSliderViewHolder>() {

    private var listItem = list

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SecondSliderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_main_second_slider, parent, false)
        return SecondSliderViewHolder(view)
    }

    override fun getItemCount(): Int = listItem.size

    override fun onBindViewHolder(holder: SecondSliderViewHolder, position: Int) {

        holder.itemView.setOnClickListener {
            val bundle = bundleOf("url" to Constants.slider_url+listItem[position].url.toString())
            bundle.putString("title", listItem[position].title)
            Navigation.findNavController(it)
                .navigate(R.id.action_home_to_WebViewerFragment, bundle)
        }

        holder.bind(listItem[position])

    }


}