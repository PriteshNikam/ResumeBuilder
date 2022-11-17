package com.example.buildresume.ui.homescreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.buildresume.UtilClass.gotoScreen
import com.example.buildresume.UtilClass.showLog
import com.example.buildresume.data.Resume
import com.example.buildresume.databinding.FragmentHomeScreenBinding
import com.example.buildresume.viewmodel.ResumeViewModel
import com.google.android.material.appbar.AppBarLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeScreenFragment : Fragment(), HomeScreenRecyclerAdapter.IResumeAdapter {

    private lateinit var fragmentArgs: HomeScreenFragmentArgs
    private lateinit var binding: FragmentHomeScreenBinding

    private val resumeViewModel: ResumeViewModel by activityViewModels()

    val homeScreenRecyclerAdapter = HomeScreenRecyclerAdapter(this)

    var isItemSelected = 0

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (isItemSelected == 1) {
                homeScreenRecyclerAdapter.deselectItem()
                isItemSelected = 0
            } else {
                requireActivity().finish()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

      //  requireActivity()

        fragmentArgs = HomeScreenFragmentArgs.fromBundle(requireArguments())
        requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeScreenBinding.inflate(inflater, container, false)

        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)

        binding.run {
            textViewUserNameHomeScreen.text = fragmentArgs.user?.displayName
            recyclerViewHomeScreen.layoutManager = GridLayoutManager(requireContext(), 2)
            recyclerViewHomeScreen.adapter = homeScreenRecyclerAdapter
        }
            return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateHomeScreenView()
        createResume()
    }

    private fun createResume() {
        binding.floatingButtonAddResumeHomeScreen.setOnClickListener {
            val resume = Resume()
            resumeViewModel.setRecyclerViewResume(resume)
            findNavController().navigate(HomeScreenFragmentDirections.actionHomeScreenFragmentToFormSlideMainFragment())
        }
    }

    private fun updateHomeScreenView() {
        binding.apply {
            resumeViewModel.allResumeList.observe(requireActivity()) {
                if (it.isNotEmpty()) {
                    recyclerViewHomeScreen.visibility = View.VISIBLE
                    textViewUserNameHomeScreen.text = fragmentArgs.user?.displayName
                    textViewEmptyTextHomeScreen.visibility = View.INVISIBLE
                    imageViewEmptyDocHomeScreen.visibility = View.INVISIBLE
                    homeScreenRecyclerAdapter.updateList(it)
                } else {
                    recyclerViewHomeScreen.visibility = View.INVISIBLE
                    textViewEmptyTextHomeScreen.visibility = View.VISIBLE
                    imageViewEmptyDocHomeScreen.visibility = View.VISIBLE
                    homeScreenRecyclerAdapter.updateList(it)
                }
            }
        }
    }

    override fun onClickOpenResume(resume: Resume) {
        resumeViewModel.setRecyclerViewResume(resume)
        findNavController().navigate(HomeScreenFragmentDirections.actionHomeScreenFragmentToFormSlideMainFragment())
    }

    override fun onClickDeleteResume(resume: Resume) {
        resumeViewModel.deleteResume(resume)
       // binding.recyclerViewHomeScreen.recycledViewPool.clear()
        homeScreenRecyclerAdapter.deselectItem()
    }

    override fun isItemSelected() {
        isItemSelected = 1
    }

}