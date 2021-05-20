package com.app.maktabielektroni.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.app.maktabielektroni.R
import com.app.maktabielektroni.models.response.Themes
import com.app.maktabielektroni.viewHolder.ThemeFragmentListViewHolder
import org.jsoup.Jsoup

class ThemeFragmentListAdapter : RecyclerView.Adapter<ThemeFragmentListViewHolder>() {

    private var listThemes = mutableListOf<Themes>()

    fun setDataList(data: MutableList<Themes>) {
        this.listThemes = data
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ThemeFragmentListViewHolder {
        val view = LayoutInflater.from(parent.context)
        val binding = com.app.maktabielektroni.databinding.FragmentThemeListItemBinding.inflate(
            view,
            parent,
            false
        )
        return ThemeFragmentListViewHolder(binding)
    }

    override fun getItemCount(): Int = listThemes.size

    override fun onBindViewHolder(holder: ThemeFragmentListViewHolder, position: Int) {
        var flag = true
        holder.itemView.setOnClickListener {

            val bundle = bundleOf("theme_id" to listThemes[position].id.toString())

            Log.d("itemData", listThemes[position].id.toString())
            holder.binding.themeDescriptionTextView.text =
                removeHtmlTags(holder.binding.themeDescriptionTextView.text as String?)

            if (flag) {
                holder.binding.themeDescriptionTextView.visibility = View.VISIBLE
                holder.binding.btnDetail.visibility = View.VISIBLE
                holder.binding.expandArrow.rotation = 180f
                holder.binding.themeFragmentListItemCardView.setCardBackgroundColor(
                    ContextCompat.getColor(
                        it.context,
                        R.color.greyCard
                    )
                )
                flag = false

            } else if (!flag) {
                holder.binding.themeDescriptionTextView.visibility = View.GONE
                holder.binding.btnDetail.visibility = View.GONE
                holder.binding.expandArrow.rotation = 0f
                holder.binding.themeFragmentListItemCardView.setCardBackgroundColor(
                    ContextCompat.getColor(
                        it.context,
                        R.color.background
                    )
                )
                flag = true
            }
            holder.binding.btnDetail.setOnClickListener {
                Navigation.findNavController(it)
                    .navigate(R.id.action_themeFragmentList_to_themeDetailFragment, bundle)
            }

        }

        holder.bind(listThemes[position])

    }

    fun removeHtmlTags(html: String?): String? {
        return Jsoup.parse(html).text()
    }


}