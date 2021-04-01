package com.example.metproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.metproject.adapters.ClassesFragmentBottomSheetDialogCardAdapter
//import com.example.metproject.adapters.SubjectsFragmentBottomSheetDialogCardAdapter
import com.example.metproject.adapters.eminem
import com.example.metproject.databinding.FragmentClassesBottomSheetDialogBinding
//import com.example.metproject.databinding.FragmentSuBottomSheetDialogBinding
import com.example.metproject.databinding.FragmentSubjectsBottomSheetDialogBinding
import com.example.metproject.models.ClassesFragmentCardModel
//import com.example.metproject.models.SubjectsFragmentCardModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ClassesBottomSheetDialogFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentClassesBottomSheetDialogBinding

    private val listCards = mutableListOf<ClassesFragmentCardModel>()

    private lateinit var adapter: ClassesFragmentBottomSheetDialogCardAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dataBinding = DataBindingUtil.inflate<FragmentClassesBottomSheetDialogBinding>(
            inflater,
            R.layout.fragment_classes_bottom_sheet_dialog,
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

        adapter = ClassesFragmentBottomSheetDialogCardAdapter(requireParentFragment())
        val llm = GridLayoutManager(this.context,2)
        binding.rvBottomsheet.layoutManager = llm
        binding.rvBottomsheet.adapter = adapter
        fillListCards()
        adapter.set(listCards)

        Toast.makeText(requireContext(), eminem.toString(), Toast.LENGTH_LONG).show()

    }

    private fun fillListCards() {
//        listCards.add(
//            ClassesFragmentCardModel(
//                1,
//                "1"
//            )
//        )
//        listCards.add(
//            ClassesFragmentCardModel(
//                2,
//                "2"
//            )
//        )
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

}