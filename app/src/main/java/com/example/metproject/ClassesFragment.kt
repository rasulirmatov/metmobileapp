package com.example.metproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.metproject.adapters.ClassesFragmentCardAdapter
import com.example.metproject.adapters.SubjectsFragmentCardAdapter
import com.example.metproject.databinding.FragmentClassesBinding
import com.example.metproject.databinding.FragmentSubjectsBinding
import com.example.metproject.models.ClassesFragmentCardModel
import com.example.metproject.models.SubjectsFragmentCardModel
import kotlinx.android.synthetic.main.fragment_classes.*


class ClassesFragment : Fragment() {

    private lateinit var binding: FragmentClassesBinding

    private val listCards = mutableListOf<ClassesFragmentCardModel>()

    private lateinit var adapter: ClassesFragmentCardAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dataBinding = DataBindingUtil.inflate<FragmentClassesBinding>(
            inflater,
            R.layout.fragment_classes,
            container,
            false
        )
        dataBinding.lifecycleOwner = this
        binding = dataBinding
        setHasOptionsMenu(true)
        listCards.clear()
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.classesToolbar.setNavigationIcon(R.drawable.ic_icon_go_to_back)
        binding.classesToolbar.setNavigationOnClickListener(View.OnClickListener {
            requireActivity().onBackPressed()
        })

        adapter = ClassesFragmentCardAdapter()
        val llm = GridLayoutManager(this.context,2)
        binding.rvClasses.layoutManager = llm
        binding.rvClasses.adapter = adapter
        fillListCards()
        adapter.set(listCards)


    }


    private fun fillListCards() {
        var i = 1
        while (i <= 11) {
            listCards.add(
                ClassesFragmentCardModel(
                    i,
                    i.toString()
                )
            )
            i++
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}