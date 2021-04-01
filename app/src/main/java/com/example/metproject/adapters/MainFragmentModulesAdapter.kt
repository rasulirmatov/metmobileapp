package com.example.metproject.adapters

import com.example.metproject.models.MainFragmentModulesModel
import com.example.metproject.viewHolder.MainFragmentModulesViewHolder
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.metproject.R
import com.example.metproject.models.SubjectsFragmentCardModel
import com.example.metproject.viewHolder.SubjectsFragmentCardViewHolder
import kotlin.coroutines.coroutineContext
import kotlin.math.log

class MainFragmentModulesAdapter : RecyclerView.Adapter<MainFragmentModulesViewHolder>() {

    private val listModules = mutableListOf<MainFragmentModulesModel>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MainFragmentModulesViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_main_mudules_item, parent, false)
        return MainFragmentModulesViewHolder(view)
    }

    override fun getItemCount(): Int = listModules.size

    override fun onBindViewHolder(holder: MainFragmentModulesViewHolder, position: Int) {

        holder.itemView.setOnClickListener {

            Toast.makeText(
                holder.module_name.context,
                listModules[position].module_id.toString(),
                Toast.LENGTH_SHORT
            ).show()

            Log.d("itemData", listModules[position].module_id.toString())

//          Navigation.findNavController(it).navigate(R.id.signInFragment)

        }

        holder.bind(listModules[position])

    }

    fun set(list: MutableList<MainFragmentModulesModel>) {
        this.listModules.clear()
        this.listModules.addAll(list)
        notifyDataSetChanged()
    }

}