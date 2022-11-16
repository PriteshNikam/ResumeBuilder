package com.example.buildresume.ui.formeditorscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.buildresume.databinding.FragmentFormSlideMainBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class FormSlideMainFragment : Fragment() {

    private lateinit var binding:FragmentFormSlideMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding =  FragmentFormSlideMainBinding.inflate(inflater,container,false)
        binding.run {
            viewpagerFormSlide.adapter = ViewPagerAdapter(requireActivity().supportFragmentManager,lifecycle)
            tabLayoutFormSlide.tabMode = TabLayout.MODE_SCROLLABLE

            TabLayoutMediator(tabLayoutFormSlide, viewpagerFormSlide) { tab, position ->
                when (position) {
                    0 -> tab.text = "Profile"
                    1 -> tab.text = "Education"
                    2 -> tab.text = "Skills"
                    3 -> tab.text = "Project"
                    4 -> tab.text = "Experience"
                }
            }.attach()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}