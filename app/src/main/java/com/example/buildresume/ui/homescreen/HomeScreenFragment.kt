package com.example.buildresume.ui.homescreen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.buildresume.R
import com.example.buildresume.UtilClass.gotoScreen
import com.example.buildresume.UtilClass.showLog
import com.example.buildresume.databinding.FragmentHomeScreenBinding
import com.example.buildresume.viewmodel.ResumeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeScreenFragment : Fragment() {

    private lateinit var fragmentArgs: HomeScreenFragmentArgs
    private lateinit var binding: FragmentHomeScreenBinding

    private val resumeViewModel: ResumeViewModel by activityViewModels()

    var isItemSelected = 0

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (isItemSelected == 1) {
                isItemSelected = 0
                openEditedResume()
                //resumeViewModel.setIsResumeCreated(true)
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
        fragmentArgs = HomeScreenFragmentArgs.fromBundle(requireArguments())
        requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeScreenBinding.inflate(inflater, container, false)
        binding.textViewUserNameHomeScreen.text = fragmentArgs.user?.displayName
        showLog("lifeCycle","onCreateView")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showLog("lifeCycle","onViewCreated")
        readStoredData()

        resumeViewModel.resumeCreated.observe(viewLifecycleOwner, Observer {
            if (resumeViewModel.form.isFormFilled()) {
                binding.apply {
                    singleResumeViewConstraint.visibility = View.VISIBLE
                    floatingButtonAddResumeHomeScreen.visibility = View.GONE
                    textViewUserNameHomeScreen.text = resumeViewModel.form.userName
                    textViewResumeUserNameHomeScreen.text = resumeViewModel.form.userName
                    textViewEmptyTextHomeScreen.visibility = View.GONE
                    imageViewEmptyDocHomeScreen.visibility = View.GONE
                }
            } else {
                binding.apply {
                    singleResumeViewConstraint.visibility = View.GONE
                    floatingButtonAddResumeHomeScreen.visibility = View.VISIBLE
                    textViewEmptyTextHomeScreen.visibility = View.VISIBLE
                    imageViewEmptyDocHomeScreen.visibility = View.VISIBLE
                }
            }
        })

        showLog("lifeCycle resume:","${resumeViewModel.resumeCreated.value}")
        openEditedResume()
        deletedEditedResume()
        createNewResume()

    }

    private fun createNewResume() {
        binding.floatingButtonAddResumeHomeScreen.setOnClickListener {
            gotoScreen(
                view,
                HomeScreenFragmentDirections.actionHomeScreenFragmentToFormEditorScreenFragment()
            )
        }
    }

    override fun onDestroy() {
        showLog("lifeCycle","onDestroy")
        super.onDestroy()
    }

    override fun onResume() {
        showLog("lifeCycle","onResume")
        super.onResume()
    }

    private fun deletedEditedResume() {
        binding.apply {
            singleResumeViewConstraint.setOnLongClickListener {
                if (isItemSelected == 0) {
                    isItemSelected = 1
                    singleResumeViewConstraint.setBackgroundResource(R.drawable.delete_resume_shape)
                    textViewResumeUserNameHomeScreen.visibility = View.INVISIBLE
                    imageVIewResumeDocumentView.visibility = View.GONE
                    imageViewDeleteResume.visibility = View.VISIBLE
                    binding.apply {
                        singleResumeViewConstraint.setOnClickListener {
                            if (isItemSelected == 1) {
                                isItemSelected = 0
                                resumeViewModel.deleteLocalData()
                                readStoredData()
                                resumeViewModel.setIsResumeCreated(false)
                                updateHomeScreenView(resumeViewModel.resumeCreated.value!!)
                                imageViewDeleteResume.visibility = View.GONE
                                binding.textViewUserNameHomeScreen.text = ""
                            }
                        }
                    }
                }
                return@setOnLongClickListener true
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
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                resumeViewModel.readToLocal.collect { resume ->
                    resumeViewModel.run {
                        form.userName = resume.userName
                        form.userMobile = resume.userMobile
                        form.userAddress = resume.userAddress
                        form.userEmail = resume.userEmail
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
                        showLog(" life cycle data read: ","${form.isFormFilled()}")
                    }
                }
           }
        }
    }

    private fun updateHomeScreenView(state:Boolean) {
        showLog("values: ", "update Screen")
            if (state) {
                binding.apply {
                    singleResumeViewConstraint.visibility = View.VISIBLE
                    floatingButtonAddResumeHomeScreen.visibility = View.GONE
                    textViewUserNameHomeScreen.text = resumeViewModel.form.userName
                    textViewResumeUserNameHomeScreen.text = resumeViewModel.form.userName
                    textViewEmptyTextHomeScreen.visibility = View.GONE
                    imageViewEmptyDocHomeScreen.visibility = View.GONE
                }
            } else {
                binding.apply {
                    singleResumeViewConstraint.visibility = View.GONE
                    floatingButtonAddResumeHomeScreen.visibility = View.VISIBLE
                    textViewEmptyTextHomeScreen.visibility = View.VISIBLE
                    imageViewEmptyDocHomeScreen.visibility = View.VISIBLE
                }
            }
    }

}