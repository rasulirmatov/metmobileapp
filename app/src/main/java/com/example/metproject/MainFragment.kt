package com.example.metproject

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.metproject.adapters.HomeSliderAdapter
import com.example.metproject.adapters.MainFragmentRecomendationAdapter
import com.example.metproject.adapters.SliderAdapter
import com.example.metproject.databinding.FragmentMainBinding
import com.example.metproject.models.HomeSliderModel
import com.example.metproject.models.MainFragmentRecomendationModel
import com.example.metproject.models.SliderModel
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView


class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private var pressedTime: Long = 0
    private lateinit var adapter: MainFragmentRecomendationAdapter
    private var RecomendationList = mutableListOf<MainFragmentRecomendationModel>()

    private lateinit var sliderAdapter: SliderAdapter


    private val homeSliderAdapter =
        HomeSliderAdapter(
            listOf(
                HomeSliderModel(
                    "Мактаби\nэлектрони\nТочикистон",
                    "Тоҷикистон воситаест, ки барои таъмин ва \n ташкили “таҳсил дар давоми ҳаёт”-ро  ҳам барои ҷавонон \n ва ҳам барои калонсолон кӯмаки худро мерасонад",
                    R.drawable.home_slider_image1
                ),
                HomeSliderModel(
                    "Мактаби \n электрони \n Точикистон",
                    "Тоҷикистон воситаест, ки барои таъмин ва \n ташкили “таҳсил дар давоми ҳаёт”-ро  ҳам барои ҷавонон \n ва ҳам барои калонсолон кӯмаки худро мерасонад",
                    R.drawable.home_slider_image2
                )
            )
        )

    private fun FillRecomendationList() {
        RecomendationList.add(
            MainFragmentRecomendationModel(
                6,
                R.drawable.biology,
                "Биология"
            )
        )
        RecomendationList.add(
            MainFragmentRecomendationModel(
                2,
                R.drawable.tarihiumumi,
                "Таърих халқи тоҷик дар замони оянда"
            )
        )

        RecomendationList.add(
            MainFragmentRecomendationModel(
                3,
                R.drawable.zanglisi,
                "Забони англиси"
            )
        )
        RecomendationList.add(
            MainFragmentRecomendationModel(
                4,
                R.drawable.znemisi,
                "Забони немиси"
            )
        )
        RecomendationList.add(
            MainFragmentRecomendationModel(
                14,
                R.drawable.geography,
                "География"
            )
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // This callback will only be called when HameFragment is at least Started.
        // Call Back for exit programm
        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            if (pressedTime + 2000 > System.currentTimeMillis()) {
                requireActivity().moveTaskToBack(true);
                requireActivity().finish();
            } else {
                Toast.makeText(context, R.string.exit_toast, Toast.LENGTH_SHORT).show()
            }
            pressedTime = System.currentTimeMillis();
        }
        callback.isEnabled

//        exitTransition = MaterialFadeThrough()
//        enterTransition = MaterialFadeThrough()

    }


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

        // Clear Duplicate
        RecomendationList.clear()

        setHasOptionsMenu(true)
        return dataBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Home Slider
//        binding.homeSliderViewPager.adapter = homeSliderAdapter



        // Recomendation List
        val llm = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
        binding.rv.layoutManager = llm
        FillRecomendationList()
        adapter = MainFragmentRecomendationAdapter(RecomendationList)
        binding.rv.adapter = adapter


        // Main Toolbar
        binding.mainToolBar.inflateMenu(R.menu.main_menu)
        binding.mainToolBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.settings -> {
                    findNavController()
                        .navigate(
                            R.id.action_home_to_settingsFragment,
                            null,
                            NavOptions.Builder()
                                .setPopUpTo(
                                    R.id.myNavHostFragment,
                                    true
                                ).build()
                        )
                    true
                }
                else -> super.onOptionsItemSelected(it)
            }
        }
        sliderAdapter = SliderAdapter(requireContext())
        renewItems(binding.imageSlider)
        binding.imageSlider.setSliderAdapter(sliderAdapter)
        binding.imageSlider.setIndicatorAnimation(IndicatorAnimationType.WORM) //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        binding.imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
        binding.imageSlider.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH)
        binding.imageSlider.setIndicatorSelectedColor(Color.WHITE)
        binding.imageSlider.setIndicatorUnselectedColor(Color.GRAY)
        binding.imageSlider.setScrollTimeInSec(5)
        binding.imageSlider.setAutoCycle(true)
        binding.imageSlider.startAutoCycle()

        binding.imageSlider.setOnIndicatorClickListener(DrawController.ClickListener {
            Log.i(
                "GGG",
                "onIndicatorClicked: " + binding.imageSlider.getCurrentPagePosition()
            )
        })

    }

    fun renewItems(view: View?) {
        val sliderItemList: MutableList<SliderModel> = ArrayList()
        //dummy data
        for (i in 0..4) {
            val sliderItem = SliderModel()
            sliderItem.description = "Slider Item $i"
//            if (i % 2 == 0) {
//                sliderItem.imageUrl = "https://images.pexels.com/photos/929778/pexels-photo-929778.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260"
//            } else {
                sliderItem.imageUrl = "https://mettj.ru/front/images/slider/s-4.jpg"
//            }
            sliderItemList.add(sliderItem)
        }
        sliderAdapter.renewItems(sliderItemList)
    }

    fun removeLastItem(view: View?) {
        if (sliderAdapter.getCount() - 1 >= 0) sliderAdapter.deleteItem(sliderAdapter.getCount() - 1)
    }

    fun addNewItem(view: View?) {
        val sliderItem = SliderModel()
        sliderItem.description = "Slider Item Added Manually"
        sliderItem.imageUrl = "https://images.pexels.com/photos/929778/pexels-photo-929778.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260"
        sliderAdapter.addItem(sliderItem)
    }


}