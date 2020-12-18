package com.example.metproject


import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.metproject.adapters.IntroSliderAdapter
import com.example.metproject.models.IntroSlide
import kotlinx.android.synthetic.main.fragment_intro_slider.*

/**
 * A simple [Fragment] subclass.
 */
class IntroSliderFragment : Fragment() {
    lateinit var preferences: SharedPreferences
    val pref_show_intro = "Intro"

    private val introSliderAdapter =
        IntroSliderAdapter(
            listOf(
                IntroSlide(
                    "ТАҲСИЛИ ЭЛЕКТРОНИ",
                    "Пваамоатмва вамлотмвамваомтвамт вамлотамамотавм вамот",
                    R.drawable.intro1
                ),
                IntroSlide(
                    "МАЪЛУМОТҲОИ ЗИЕД",
                    "Пваамоатмва вамлотмвамваомтвамт вамлотамамотавм вамот",
                    R.drawable.intro2
                ),
                IntroSlide(
                    "ДИЛХОҲ МИНТАҚА",
                    "Пваамоатмва вамлотмвамваомтвамт вамлотамамотавм вамот",
                    R.drawable.intro3
                ),
                IntroSlide(
                    "ТАЁРШАВИ БА ОЗМУН",
                    "Пваамоатмва вамлотмвамваомтвамт вамлотамамотавм вамот",
                    R.drawable.intro4
                ),
                IntroSlide(
                    "ДИЛХОҲ ПЛАТФОРМА",
                    "Пваамоатмва вамлотмвамваомтвамт вамлотамамотавм вамот",
                    R.drawable.intro5
                ),
                IntroSlide(
                    "ИНТЕРАКТИВӢ",
                    "Пваамоатмва вамлотмвамваомтвамт вамлотамамотавм вамот",
                    R.drawable.intro6
                )
            )
        )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_intro_slider, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        preferences =
            this.requireActivity().getSharedPreferences("IntroSlider", Context.MODE_PRIVATE)
        introSliderViewPager.adapter = introSliderAdapter

        if (!preferences.getBoolean(pref_show_intro, true)) {
            view?.let {
                Navigation.findNavController(it)
                    .navigate(R.id.action_introSliderFragment_to_signInFragment)
            }
        }

        setupIndicators()
        setCurrentIndicator(0)
        introSliderViewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicator(position)
                if (position == 5) {
                    buttonNext.text = "Сар кардан"
                    buttonNext.visibility = View.VISIBLE
                } else {
                    buttonNext.visibility = View.INVISIBLE
                }
            }
        })

        // Disable overscroll on View Pager "Don't show shades!"
        introSliderViewPager.apply {
            (getChildAt(0) as RecyclerView).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        }

        buttonNext.setOnClickListener {
            if (introSliderViewPager.currentItem + 1 < introSliderAdapter.itemCount) {
                introSliderViewPager.currentItem += 1
                if (introSliderViewPager.currentItem == 5) {
                    buttonNext.text = "Сар кардан"
                } else {
                    buttonNext.text = "Давом додан"
                }
            } else {
                goToDashboard(it)
            }
        }
        textSkipIntro.setOnClickListener {
            goToDashboard(it)
        }

    }

    private fun goToDashboard(view: View) {
//        val editor = preferences.edit()
//        editor.putBoolean(pref_show_intro, false)
//        editor.apply()
        view?.let {

          // Another way to show modal bottom sheet
//          val modalbutton = TermsofAgreementFragment()
//          modalbutton.show(parentFragmentManager,"Tag")

            Navigation.findNavController(it)
                .navigate(R.id.action_introSliderFragment_to_termsofAgreementFragment)


        }
    }

    private fun setCurrentIndicator(index: Int) {
        val childCount = indicatorsContainer2.childCount
        for (i in 0 until childCount) {
            val imageView = indicatorsContainer2[i] as ImageView
            if (i == index) {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.indicator_active
                    )
                )
            } else {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.indicator_inactive
                    )
                )
            }
        }
    }

    private fun setupIndicators() {
        val indicators = arrayOfNulls<ImageView>(introSliderAdapter.itemCount)
        val layoutParams: LinearLayout.LayoutParams =
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        layoutParams.setMargins(8, 0, 8, 0)
        for (i in indicators.indices) {
            indicators[i] = ImageView(requireContext())
            indicators[i].apply {
                this?.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.indicator_inactive
                    )
                )
                this?.layoutParams = layoutParams
            }
            indicatorsContainer2.addView(indicators[i])
        }
    }


}
