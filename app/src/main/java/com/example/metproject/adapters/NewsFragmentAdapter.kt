package com.example.metproject.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.metproject.R
import com.example.metproject.models.MainFragmentNewsModel
import com.example.metproject.models.MainFragmentRecomendationModel
import com.example.metproject.models.response.NewsItem
import com.example.metproject.utils.Constants
import com.example.metproject.viewHolder.MainFragmentNewsViewHolder
import com.example.metproject.viewHolder.MainFragmentRecomendationViewHolder

class NewsFragmentAdapter(list: MutableList<NewsItem>) :
    RecyclerView.Adapter<MainFragmentNewsViewHolder>() {

    private var listNews = list

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MainFragmentNewsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_news_item, parent, false)
        return MainFragmentNewsViewHolder(view)
    }

    override fun getItemCount(): Int = listNews.size

    override fun onBindViewHolder(holder: MainFragmentNewsViewHolder, position: Int) {

        holder.itemView.setOnClickListener {
            val bundle = bundleOf("url" to Constants.news_url+listNews[position].id.toString())
            bundle.putString("title", "Навгонӣ")
            Navigation.findNavController(it)
                .navigate(R.id.action_newsFragment_to_WebViewerFragment, bundle)
        }

        holder.bind(listNews[position])

    }


}