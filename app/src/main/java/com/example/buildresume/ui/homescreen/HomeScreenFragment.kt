package com.example.buildresume.ui.homescreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.buildresume.R
import com.example.buildresume.UtilClass.gotoScreen
import com.example.buildresume.databinding.FragmentHomeScreenBinding
import com.example.buildresume.viewmodel.ResumeViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeScreenFragment : Fragment() {

    private lateinit var fragmentArgs: HomeScreenFragmentArgs
    private lateinit var binding: FragmentHomeScreenBinding
    private lateinit var firebaseAuth: FirebaseAuth

    private val resumeViewModel: ResumeViewModel by activityViewModels()

    var isItemSelected = 0

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (isItemSelected == 1) {
                isItemSelected = 0
                openEditedResume()
                binding.run {
                    singleResumeViewConstraint.setBackgroundResource(R.drawable.custom_view_shape)
                    textViewResumeUserNameHomeScreen.visibility = View.VISIBLE
                    imageVIewResumeDocumentView.visibility = View.VISIBLE
                    imageViewDeleteResume.visibility = View.GONE
                }
            } else {
                requireActivity().finish()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.actionBar?.title = "Build Resume"
        fragmentArgs = HomeScreenFragmentArgs.fromBundle(requireArguments())
        requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeScreenBinding.inflate(inflater, container, false)
        binding.textViewUserNameHomeScreen.text = fragmentArgs.user?.displayName
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        readStoredData()
        updateHomeScreenView()
        openEditedResume()
        deleteEditedResume()
        createResume()
    }

    private fun createResume() {
        binding.floatingButtonAddResumeHomeScreen.setOnClickListener {
            gotoScreen(
                view,
                HomeScreenFragmentDirections.actionHomeScreenFragmentToFormEditorScreenFragment()
            )
        }
    }

    private fun deleteEditedResume() {
        binding.run {
            resumeViewModel.run {
                singleResumeViewConstraint.setOnLongClickListener {
                    if (isItemSelected == 0) {
                        isItemSelected = 1
                        singleResumeViewConstraint.setBackgroundResource(R.drawable.delete_resume_shape)
                        textViewResumeUserNameHomeScreen.visibility = View.INVISIBLE
                        imageVIewResumeDocumentView.visibility = View.GONE
                        imageViewDeleteResume.visibility = View.VISIBLE
                        singleResumeViewConstraint.setOnClickListener {
                            if (isItemSelected == 1) {
                                isItemSelected = 0
                                deleteLocalData()
                                readStoredData()
                                imageViewDeleteResume.visibility = View.GONE
                            }
                        }
                    }
                    return@setOnLongClickListener true
                }
            }
        }
    }

    private fun openEditedResume() {
        binding.singleResumeViewConstraint.setOnClickListener {
            if (isItemSelected == 0) {
                gotoScreen(
                    view,
                    HomeScreenFragmentDirections.actionHomeScreenFragmentToFormEditorScreenFragment()
                )
            }
        }
    }

    private fun readStoredData() {
        viewLifecycleOwner.lifecycleScope.launch {
           // lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                resumeViewModel.run {
                    readToLocal.collect { resume ->
                        form.userName = resume.userName
                        form.userMobile = resume.userMobile
                        form.userEmail = resume.userEmail
                        form.userAddress = resume.userAddress
                        form.schoolName = resume.schoolName
                        form.schoolMarks = resume.schoolMarks
                        form.collegeName = resume.collegeName
                        form.collegeMarks = resume.collegeMarks
                        form.diplomaCollegeName = resume.diplomaCollegeName
                        form.diplomaCollegeMarks = resume.diplomaCollegeMarks
                        form.degreeCollegeName = resume.degreeCollegeName
                        form.degreeMarks = resume.degreeMarks
                        form.programmingLanguage = resume.programmingLanguage
                        form.softwareTools = resume.softwareTools
                        form.certification = resume.certification
                        form.otherSkills = resume.otherSkills
                        form.projectTitle = resume.projectTitle
                        form.projectDescription = resume.projectDescription
                        form.companyName = resume.companyName
                        form.companyExperienceYear = resume.companyExperienceYear
                        form.totalExperience = resume.totalExperience
                        if (form.isFormFilled()) {
                            setIsResumeCreated(true)
                        }
                        else{
                            setIsResumeCreated(false)
                        }
                    }
                }
          //  }
        }
    }

    private fun updateHomeScreenView() {
        binding.apply {
            resumeViewModel.run {
                resumeCreated.observe(viewLifecycleOwner, Observer {
                    if (form.isFormFilled()) {
                        singleResumeViewConstraint.visibility = View.VISIBLE
                        floatingButtonAddResumeHomeScreen.visibility = View.INVISIBLE
                        textViewUserNameHomeScreen.text = fragmentArgs.user?.displayName
                        textViewResumeUserNameHomeScreen.text = form.userName
                        textViewEmptyTextHomeScreen.visibility = View.INVISIBLE
                        imageViewEmptyDocHomeScreen.visibility = View.INVISIBLE
                    } else {
                        singleResumeViewConstraint.visibility = View.GONE
                        floatingButtonAddResumeHomeScreen.visibility = View.VISIBLE
                        textViewEmptyTextHomeScreen.visibility = View.VISIBLE
                        imageViewEmptyDocHomeScreen.visibility = View.VISIBLE
                    }
                })
            }
        }
    }
}