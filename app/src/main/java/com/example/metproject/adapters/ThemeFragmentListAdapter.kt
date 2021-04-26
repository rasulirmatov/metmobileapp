package com.example.metproject.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.metproject.R
import com.example.metproject.models.SubjectsFragmentCardModel
import com.example.metproject.models.ThemeFragmentListModel
import com.example.metproject.models.response.ClassesItem
import com.example.metproject.models.response.Themes
import com.example.metproject.viewHolder.ClassesFragmentCardViewHolder
import com.example.metproject.viewHolder.SubjectsFragmentCardViewHolder
import com.example.metproject.viewHolder.ThemeFragmentListViewHolder
import kotlin.properties.Delegates

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
        val binding = com.example.metproject.databinding.FragmentThemeListItemBinding.inflate(
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

//            Toast.makeText(
//                holder.theme_number.context,
//                listThemes[position].theme_id.toString(),
//                Toast.LENGTH_SHORT
//            ).show()

            Log.d("itemData", listThemes[position].id.toString())

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
                    .navigate(R.id.action_themeFragmentList_to_themeDetailFragment)
            }

        }

        holder.bind(listThemes[position])

    }


}