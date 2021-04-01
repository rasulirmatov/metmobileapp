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
import com.example.metproject.viewHolder.SubjectsFragmentCardViewHolder
import com.example.metproject.viewHolder.ThemeFragmentListViewHolder
import kotlin.properties.Delegates

class ThemeFragmentListAdapter : RecyclerView.Adapter<ThemeFragmentListViewHolder>() {

    private val listThemes = mutableListOf<ThemeFragmentListModel>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ThemeFragmentListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_theme_list_item, parent, false)
        return ThemeFragmentListViewHolder(view)
    }

    override fun getItemCount(): Int = listThemes.size

    override fun onBindViewHolder(holder: ThemeFragmentListViewHolder, position: Int) {
        var flag = true
        holder.itemView.setOnClickListener {

            Toast.makeText(
                holder.theme_number.context,
                listThemes[position].theme_id.toString(),
                Toast.LENGTH_SHORT
            ).show()

            Log.d("itemData", listThemes[position].theme_id.toString())

            if (flag) {
                holder.theme_descritpion.visibility = View.VISIBLE
                holder.theme_btn.visibility = View.VISIBLE
                holder.theme_expand_arrow.rotation = 180f
                holder.theme_card.setCardBackgroundColor(
                    ContextCompat.getColor(
                        it.context,
                        R.color.greyCard
                    )
                )
                flag = false

            } else if (!flag) {
                holder.theme_descritpion.visibility = View.GONE
                holder.theme_btn.visibility = View.GONE
                holder.theme_expand_arrow.rotation = 0f
                holder.theme_card.setCardBackgroundColor(
                    ContextCompat.getColor(
                        it.context,
                        R.color.background
                    )
                )
                flag = true
            }
            holder.theme_btn.setOnClickListener{
                Navigation.findNavController(it).navigate(R.id.action_themeFragmentList_to_themeDetailFragment)
            }


//            Navigation.findNavController(it).navigate(R.id.action_subjects_to_classesBottomSheetDialogFragment)

        }




        holder.bind(listThemes[position])

    }


    fun set(list: MutableList<ThemeFragmentListModel>) {
        this.listThemes.clear()
        this.listThemes.addAll(list)
        notifyDataSetChanged()
    }

}