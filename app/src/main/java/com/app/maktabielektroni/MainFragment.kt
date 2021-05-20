package com.app.maktabielektroni

import android.content.Intent
import android.graphics.*
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.maktabielektroni.adapters.HomeSliderAdapter
import com.app.maktabielektroni.adapters.MainFragmentNewsAdapter
import com.app.maktabielektroni.adapters.SecondSliderAdapter
import com.app.maktabielektroni.adapters.SliderAdapter
import com.app.maktabielektroni.databinding.FragmentMainBinding
import com.app.maktabielektroni.models.HomeSliderModel
import com.app.maktabielektroni.models.response.*
import com.app.maktabielektroni.utils.Constants
import com.app.maktabielektroni.viewModels.MainFragmentViewModel
import com.rajat.pdfviewer.PdfViewerActivity
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView


class MainFragment : BaseFragment() {

    private lateinit var binding: FragmentMainBinding
    private var pressedTime: Long = 0
    private lateinit var adapter: MainFragmentNewsAdapter
    private lateinit var adapter_second_slider: SecondSliderAdapter
    private var countLoadedComponent: Int = 0

//    private var RecomendationList = mutableListOf<MainFragmentNewsModel>()

    private val viewModel: MainFragmentViewModel by lazy {
        ViewModelProviders.of(this).get(MainFragmentViewModel::class.java)
    }

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

//    private fun FillRecomendationList() {
//        RecomendationList.add(
//            MainFragmentNewsModel(
//                6,
//                R.drawable.biology,
//                "Биология"
//            )
//        )
//    }


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
//        RecomendationList.clear()
        setHasOptionsMenu(true)
        return dataBinding.root
    }


    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Home Slider
//        binding.homeSliderViewPager.adapter = homeSliderAdapter

        val llm = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvNews.layoutManager = llm

        val glm = GridLayoutManager(requireContext(), 2)
        binding.rvSecondSlider.layoutManager = glm


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
                R.id.for_students -> {
                    findNavController().navigate(
                        R.id.action_home_to_WebViewerFragment,
                        bundleForWebViewer("Ба хонанда", Constants.slider_url + "/page/ba-talaba"),
                        NavOptions.Builder()
                            .setPopUpTo(
                                R.id.myNavHostFragment,
                                true
                            ).build()
                    )
                    true
                }
                R.id.for_school -> {
                    findNavController().navigate(
                        R.id.action_home_to_WebViewerFragment,
                        bundleForWebViewer("Ба мактаб", Constants.slider_url + "/page/ba-maktab"),
                        NavOptions.Builder()
                            .setPopUpTo(
                                R.id.myNavHostFragment,
                                true
                            ).build()
                    )
                    true
                }
                R.id.for_teachers -> {
                    findNavController().navigate(
                        R.id.action_home_to_WebViewerFragment,
                        bundleForWebViewer(
                            "Ба муаллим",
                            Constants.slider_url + "/page/ba-muallimon"
                        ),
                        NavOptions.Builder()
                            .setPopUpTo(
                                R.id.myNavHostFragment,
                                true
                            ).build()
                    )
                    true
                }
                R.id.for_parents -> {
                    findNavController().navigate(
                        R.id.action_home_to_WebViewerFragment,
                        bundleForWebViewer(
                            "Ба волидайн",
                            Constants.slider_url + "/page/ba-volidayn"
                        ),
                        NavOptions.Builder()
                            .setPopUpTo(
                                R.id.myNavHostFragment,
                                true
                            ).build()
                    )
                    true
                }
                R.id.about_app -> {
                    activity?.let {
                        val intent = Intent (it, AboutAppActivity::class.java)
                        it.startActivity(intent)
                    }
                    true
                }

                else -> super.onOptionsItemSelected(it)
            }
        }

        checkNetworkandLoadData()

        binding.showMore.setOnClickListener {
            findNavController()
                .navigate(
                    R.id.action_home_to_newsFragment,
                    null,
                    NavOptions.Builder()
                        .setPopUpTo(
                            R.id.myNavHostFragment,
                            true
                        ).build()
                )
        }

        binding.swiperefresh.setOnRefreshListener {
            binding.allItemsContainer.visibility = View.GONE
            checkNetworkandLoadData()
        }

        binding.btnReload.setOnClickListener {
            showProgressDialog()
            binding.networkProblemLayout.visibility = View.GONE
            checkNetworkandLoadData()
        }

    }

    fun checkNetworkandLoadData() {
        if (isNetworkAvailable()) {
            showProgressDialog()
            makeApiCallMainSlider()
            makeApiCallSecondSlider()
            makeApiCallNews()
            binding.networkProblemLayout.visibility = View.GONE
        } else {
            binding.swiperefresh.isRefreshing = false
            hideProgressDialog()
            binding.connectivityTextMessage.text = "Пайвастшавӣ бо интернет гум!"
            binding.animationView.setAnimation(R.raw.anim_no_internet)
            binding.networkProblemLayout.visibility = View.VISIBLE
            binding.allItemsContainer.visibility = View.GONE
        }
    }

    fun makeApiCallNews(): MainFragmentViewModel {
        viewModel.getRecyclerListDataObserver()
            .observe(viewLifecycleOwner, Observer<ResponseNews> {
                binding.swiperefresh.isRefreshing = false
                if (it != null) {
                    countLoadedComponent+=1
                    adapter =
                        MainFragmentNewsAdapter(
                            it.data.toMutableList().subList(0, getNewsSize(it.data.toMutableList()))
                        )
                    binding.rvNews.adapter = adapter
                    if (countLoadedComponent==3) {
                        hideProgressDialog()
                        binding.allItemsContainer.visibility = View.VISIBLE
                        countLoadedComponent = 0
                    }
                } else {
                    showProgressDialog()
                    binding.allItemsContainer.visibility = View.GONE
                    binding.connectivityTextMessage.text = "Корҳои техники дар система :("
                    binding.animationView.setAnimation(R.raw.anim_technical_problem)
                    binding.networkProblemLayout.visibility = View.VISIBLE
                    hideProgressDialog()
                }
            })
        viewModel.makeAPICall(requireContext())
        return viewModel
    }

    fun makeApiCallMainSlider(): MainFragmentViewModel {
        viewModel.getMainSliderListDataObserver()
            .observe(viewLifecycleOwner, Observer<ResponseMainSlider> {
                if (it != null) {
                    countLoadedComponent+=1
                    setSliderContent(it.data.value.slides.toMutableList())
                    binding.imageSlider.visibility = View.VISIBLE
                    if (countLoadedComponent==3) {
                        hideProgressDialog()
                        binding.allItemsContainer.visibility = View.VISIBLE
                        countLoadedComponent = 0
                    }
                } else {
                    showProgressDialog()
                    binding.allItemsContainer.visibility = View.GONE
                    binding.connectivityTextMessage.text = "Корҳои техники дар система :("
                    binding.animationView.setAnimation(R.raw.anim_technical_problem)
                    binding.networkProblemLayout.visibility = View.VISIBLE
                    hideProgressDialog()
                }
            })
        viewModel.makeAPICallMainslider(requireContext())
        return viewModel
    }

    fun makeApiCallSecondSlider(): MainFragmentViewModel {
        viewModel.getSecondSliderListDataObserver()
            .observe(viewLifecycleOwner, Observer<ResponseSecondSlider> {
                if (it != null) {
                    countLoadedComponent+=1
                    adapter_second_slider =
                        SecondSliderAdapter(it.data.value.slides.toMutableList())
                    binding.rvSecondSlider.adapter = adapter_second_slider
                    if (countLoadedComponent==3) {
                        hideProgressDialog()
                        binding.allItemsContainer.visibility = View.VISIBLE
                        countLoadedComponent = 0
                    }
                } else {
                    showProgressDialog()
                    binding.allItemsContainer.visibility = View.GONE
                    binding.connectivityTextMessage.text = "Корҳои техники дар система :("
                    binding.animationView.setAnimation(R.raw.anim_technical_problem)
                    binding.networkProblemLayout.visibility = View.VISIBLE
                    hideProgressDialog()
                }
            })
        viewModel.makeAPICallSecondslider(requireContext())
        return viewModel
    }


    fun getNewsSize(list: MutableList<NewsItem>): Int {
        if (list.size < 5) {
            return list.size
        } else {
            return 5
        }
    }

    fun setSliderContent(slide: MutableList<Slide>) {
        sliderAdapter = SliderAdapter(requireContext())
        sliderAdapter.setSliderItems(slide)
        sliderAdapter.notifyDataSetChanged()
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
                "IndicatorClickEvent",
                "onIndicatorClicked: " + binding.imageSlider.getCurrentPagePosition()
            )
        })
    }


}