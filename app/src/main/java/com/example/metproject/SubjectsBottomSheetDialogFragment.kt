package com.example.metproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.metproject.adapters.SubjectsFragmentBottomSheetDialogCardAdapter
import com.example.metproject.adapters.eminem
//import com.example.metproject.databinding.FragmentSuBottomSheetDialogBinding
import com.example.metproject.databinding.FragmentSubjectsBottomSheetDialogBinding
import com.example.metproject.models.SubjectsFragmentCardModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SubjectsBottomSheetDialogFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentSubjectsBottomSheetDialogBinding

    private val listCards = mutableListOf<SubjectsFragmentCardModel>()

    private lateinit var adapter: SubjectsFragmentBottomSheetDialogCardAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dataBinding = DataBindingUtil.inflate<FragmentSubjectsBottomSheetDialogBinding>(
            inflater,
            R.layout.fragment_subjects_bottom_sheet_dialog,
            container,
            false
        )
        dataBinding.lifecycleOwner = this
        binding = dataBinding
        setHasOptionsMenu(true)
        listCards.clear()
        return dataBinding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.CustomBottomSheetDialogTheme)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = SubjectsFragmentBottomSheetDialogCardAdapter(requireParentFragment())
        val llm = GridLayoutManager(this.context,2)
        binding.rvBottomsheet.layoutManager = llm
        binding.rvBottomsheet.adapter = adapter
        fillListCards()
        adapter.set(listCards)

        Toast.makeText(requireContext(),eminem.toString(),Toast.LENGTH_LONG).show()

    }

    private fun fillListCards() {
        listCards.add(
            SubjectsFragmentCardModel(
                1,
                R.drawable.biology,
                "Биология"
            )
        )
        listCards.add(
            SubjectsFragmentCardModel(
                2,
                R.drawable.tarihiumumi,
                "Таърихи умуми"
            )
        )

        listCards.add(
            SubjectsFragmentCardModel(
                3,
                R.drawable.zanglisi,
                "Забони англиси"
            )
        )
        listCards.add(
            SubjectsFragmentCardModel(
                5,
                R.drawable.znemisi,
                "Забони немиси"
            )
        )
        listCards.add(
            SubjectsFragmentCardModel(
                19,
                R.drawable.geography,
                "География"
            )
        )
    }

}