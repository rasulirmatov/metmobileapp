package com.example.metproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.metproject.adapters.SubjectsFragmentCardAdapter
import com.example.metproject.adapters.ThemeFragmentListAdapter
import com.example.metproject.databinding.FragmentSubjectsBinding
import com.example.metproject.databinding.FragmentThemeListBinding
import com.example.metproject.models.SubjectsFragmentCardModel
import com.example.metproject.models.ThemeFragmentListModel
import kotlinx.android.synthetic.main.fragment_theme_list.*

class ThemeFragmentList : Fragment() {

    private lateinit var binding: FragmentThemeListBinding

    private val listCards = mutableListOf<ThemeFragmentListModel>()

    private lateinit var adapter: ThemeFragmentListAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dataBinding = DataBindingUtil.inflate<FragmentThemeListBinding>(
            inflater,
            R.layout.fragment_theme_list,
            container,
            false
        )
        dataBinding.lifecycleOwner = this
        binding = dataBinding
        setHasOptionsMenu(true)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ThemeFragmentListAdapter()
        val llm = LinearLayoutManager(this.context)
        binding.rvThemes.layoutManager = llm
        binding.rvThemes.adapter = adapter
        binding.rvThemes.addItemDecoration(DividerItemDecoration(binding.rvThemes.context,DividerItemDecoration.VERTICAL))
        fillListCards()
        adapter.set(listCards)


        binding.themesToolbar.setNavigationIcon(R.drawable.ic_icon_go_to_back)
        binding.themesToolbar.setNavigationOnClickListener(View.OnClickListener {
                requireActivity().onBackPressed()
        })


    }

    private fun fillListCards() {
        listCards.add(
            ThemeFragmentListModel(
                1,
                "Мавзӯи 1",
                "Простейшая арифметика 1 класс",
                "Ldskdsdjsddsvdsjvndsvkjnsdvjndsjvndsjkvndsjnvjsdnvkjdsnvjkdsnjvkdsnjvkndsvssd dsvkjsdvdsjvndjkvndkjvnsdv dkjvdsnvkjdsnvjdsvn"
            )
        )
        listCards.add(
            ThemeFragmentListModel(
                1,
                "Мавзӯи 1",
                "Простейшая арифметика 1 класс",
                "Ldskdsdjsddsvdsjvndsvkjnsdvjndsjvndsjkvndsjnvjsdnvkjdsnvjkdsnjvkdsnjvkndsvssd dsvkjsdvdsjvndjkvndkjvnsdv dkjvdsnvkjdsnvjdsvn"
            )
        )
        listCards.add(
            ThemeFragmentListModel(
                1,
                "Мавзӯи 1",
                "Простейшая арифметика 1 класс",
                "Ldskdsdjsddsvdsjvndsvkjnsdvjndsjvndsjkvndsjnvjsdnvkjdsnvjkdsnjvkdsnjvkndsvssd dsvkjsdvdsjvndjkvndkjvnsdv dkjvdsnvkjdsnvjdsvn"
            )
        )
        listCards.add(
            ThemeFragmentListModel(
                1,
                "Мавзӯи 1",
                "Простейшая арифметика 1 класс",
                "Ldskdsdjsddsvdsjvndsvkjnsdvjndsjvndsjkvndsjnvjsdnvkjdsnvjkdsnjvkdsnjvkndsvssd dsvkjsdvdsjvndjkvndkjvnsdv dkjvdsnvkjdsnvjdsvn"
            )
        )
        listCards.add(
            ThemeFragmentListModel(
                1,
                "Мавзӯи 1",
                "Простейшая арифметика 1 класс",
                "Ldskdsdjsddsvdsjvndsvkjnsdvjndsjvndsjkvndsjnvjsdnvkjdsnvjkdsnjvkdsnjvkndsvssd dsvkjsdvdsjvndjkvndkjvnsdv dkjvdsnvkjdsnvjdsvn"
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        exitTransition = MaterialFadeThrough()
//        enterTransition = MaterialFadeThrough()
    }

}