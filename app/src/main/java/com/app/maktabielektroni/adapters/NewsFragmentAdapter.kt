package com.app.maktabielektroni.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import com.app.maktabielektroni.R
import com.app.maktabielektroni.models.response.NewsItem
import com.app.maktabielektroni.utils.Constants
import com.app.maktabielektroni.viewHolder.MainFragmentNewsViewHolder

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