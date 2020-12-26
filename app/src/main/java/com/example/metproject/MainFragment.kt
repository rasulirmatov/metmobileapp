package com.example.metproject

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.metproject.adapters.HomeSliderAdapter
import com.example.metproject.adapters.IntroSliderAdapter
import com.example.metproject.databinding.FragmentMainBinding
import com.example.metproject.models.HomeSliderModel
import com.example.metproject.models.IntroSlide
import kotlinx.android.synthetic.main.fragment_intro_slider.*
import kotlinx.android.synthetic.main.fragment_main.*


class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding

    private val homeSliderAdapter =
        HomeSliderAdapter(
            listOf(
                HomeSliderModel(
                    "ТАҲСИЛИ ЭЛЕКТРОНИ",
                    "Пваамоатмва вамлотмвамваомтвамт вамлотамамотавм вамот",
                    R.drawable.home_slider_image1
                ),
                HomeSliderModel(
                    "МАЪЛУМОТҲОИ ЗИЕД",
                    "Пваамоатмва вамлотмвамваомтвамт вамлотамамотавм вамот",
                    R.drawable.home_slider_image2
                )
            )
        )


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dataBinding = DataBindingUtil.inflate<FragmentMainBinding>(
            inflater,
            R.layout.fragment_main,
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

        binding.homeSliderViewPager.adapter = homeSliderAdapter

        // Main Toolbar
        binding.mainToolBar.inflateMenu(R.menu.main_menu)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
    }

}