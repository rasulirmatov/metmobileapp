package com.example.metproject

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.metproject.adapters.HomeSliderAdapter
import com.example.metproject.adapters.MainFragmentRecomendationAdapter
import com.example.metproject.databinding.FragmentMainBinding
import com.example.metproject.models.HomeSliderModel
import com.example.metproject.models.MainFragmentRecomendationModel


class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private var pressedTime: Long = 0


    private lateinit var adapter: MainFragmentRecomendationAdapter
    private var RecomendationList = mutableListOf<MainFragmentRecomendationModel>()


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
        binding.homeSliderViewPager.adapter = homeSliderAdapter

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

    }


}