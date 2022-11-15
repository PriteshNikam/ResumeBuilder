package com.example.buildresume.ui.homescreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.buildresume.R
import com.example.buildresume.UtilClass.gotoScreen
import com.example.buildresume.UtilClass.showLog
import com.example.buildresume.data.Form
import com.example.buildresume.databinding.FragmentHomeScreenBinding
import com.example.buildresume.viewmodel.ResumeViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeScreenFragment : Fragment(),HomeScreenRecyclerAdapter.IResumeAdapter {

    private lateinit var fragmentArgs: HomeScreenFragmentArgs
    private lateinit var binding: FragmentHomeScreenBinding
    private lateinit var firebaseAuth: FirebaseAuth

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
       // activity?.actionBar?.title = "Build Resume"
        fragmentArgs = HomeScreenFragmentArgs.fromBundle(requireArguments())
        requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeScreenBinding.inflate(inflater, container, false)
        binding.textViewUserNameHomeScreen.text = fragmentArgs.user?.displayName

        binding.recyclerViewHomeScreen.layoutManager = GridLayoutManager(requireContext(),2)
        binding.recyclerViewHomeScreen.adapter = homeScreenRecyclerAdapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateHomeScreenView()
        createResume()
    }

    private fun createResume() {
        binding.floatingButtonAddResumeHomeScreen.setOnClickListener {
            val form = Form()
            resumeViewModel.setSpecificResumeFormDetails(form)
            gotoScreen(
                view,
                HomeScreenFragmentDirections.actionHomeScreenFragmentToFormEditorScreenFragment(form)
            )
            showLog("form: ","${form.userName} ${form.isFormFilled()}")
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
                    recyclerViewHomeScreen.visibility =View.INVISIBLE
                    textViewEmptyTextHomeScreen.visibility = View.VISIBLE
                    imageViewEmptyDocHomeScreen.visibility = View.VISIBLE
                    homeScreenRecyclerAdapter.updateList(it)
                }
            }
        }
    }

    override fun onClickOpenResume(form: Form) {
        resumeViewModel.setSpecificResumeFormDetails(form)
        gotoScreen(
            view,
            HomeScreenFragmentDirections.actionHomeScreenFragmentToFormEditorScreenFragment(form)
        )
        showLog("form: ","${form.userName} ${form.isFormFilled()}")

    }

    override fun onClickDeleteResume(form: Form) {
        resumeViewModel.delete(form)
        homeScreenRecyclerAdapter.deselectItem()
    }

    override fun isItemSelected() {
        isItemSelected = 1
    }
}