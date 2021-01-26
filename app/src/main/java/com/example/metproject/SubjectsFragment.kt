package com.example.metproject

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.metproject.adapters.SubjectsFragmentCardAdapter
import com.example.metproject.databinding.FragmentSubjectsBinding
import com.example.metproject.models.SubjectsFragmentCardModel
import com.google.android.material.transition.MaterialFadeThrough

class SubjectsFragment : Fragment() {

    private lateinit var binding: FragmentSubjectsBinding

    private val listCards = mutableListOf<SubjectsFragmentCardModel>()

    private lateinit var adapter: SubjectsFragmentCardAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dataBinding = DataBindingUtil.inflate<FragmentSubjectsBinding>(
            inflater,
            R.layout.fragment_subjects,
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

        adapter = SubjectsFragmentCardAdapter()
        val llm = LinearLayoutManager(this.context)
        binding.rv.layoutManager = llm
        binding.rv.adapter = adapter
        fillListCards()
        adapter.set(listCards)


        binding.subjectsToolbar.setNavigationIcon(R.drawable.ic_icon_go_to_back)
        binding.subjectsToolbar.setNavigationOnClickListener(View.OnClickListener {
            if (!binding.searchView.isIconified) {
                binding.searchView.isIconified = true
                binding.searchView.clearFocus()
            }else {
                // Go to Home Fragment
                requireActivity().onBackPressed()
            }
        })


    }

    private fun fillListCards() {
        listCards.add(
            SubjectsFragmentCardModel(
                22,
                R.drawable.biology,
                "Биология"
            )
        )
        listCards.add(
            SubjectsFragmentCardModel(
                22,
                R.drawable.tarihiumumi,
                "Таърихи умуми"
            )
        )

        listCards.add(
            SubjectsFragmentCardModel(
                22,
                R.drawable.zanglisi,
                "Забони англиси"
            )
        )
        listCards.add(
            SubjectsFragmentCardModel(
                22,
                R.drawable.znemisi,
                "Забони немиси"
            )
        )
        listCards.add(
            SubjectsFragmentCardModel(
                22,
                R.drawable.geography,
                "География"
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        exitTransition = MaterialFadeThrough()
//        enterTransition = MaterialFadeThrough()
    }

}