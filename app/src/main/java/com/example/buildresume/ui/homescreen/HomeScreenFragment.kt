package com.example.buildresume.ui.homescreen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.buildresume.R
import com.example.buildresume.UtilClass.onClick
import com.example.buildresume.UtilClass.showLog
import com.example.buildresume.databinding.FragmentHomeScreenBinding
import com.example.buildresume.viewmodel.ResumeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeScreenFragment : Fragment() {

    private lateinit var fragmentArgs: HomeScreenFragmentArgs
    private lateinit var binding: FragmentHomeScreenBinding
    //private lateinit var firebaseAuth: FirebaseAuth

    private val resumeViewModel: ResumeViewModel by activityViewModels()

    var isItemSelected = 0

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (isItemSelected == 1) {
                isItemSelected = 0
                openEditedResume()
               // readStoredData()
                resumeViewModel.setIsResumeCreated(true)
                //updateHomeScreenView()
                binding.singleResumeViewConstraint.setBackgroundResource(R.drawable.custom_view_shape)
                showLog("value: ", resumeViewModel.form.userName)
                binding.textViewResumeUserNameHomeScreen.visibility = View.VISIBLE
                binding.imageVIewResumeDocumentView.visibility = View.VISIBLE
                binding.imageViewDeleteResume.visibility = View.GONE
            } else {
                //activity?.supportFragmentManager?.popBackStackImmediate()
                requireActivity().onBackPressed()
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
        readStoredData()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showLog("lifeCycle","onViewCreated")
        readStoredData()

        resumeViewModel.resumeCreated.observe(requireActivity(), Observer {
            updateHomeScreenView(it)
        })

        openEditedResume()
        deletedEditedResume()

        showLog("isSelected", "$isItemSelected")

        binding.floatingButtonAddResumeHomeScreen.setOnClickListener {
            onClick(
                view,
                HomeScreenFragmentDirections.actionHomeScreenFragmentToFormEditorScreenFragment()
            )
        }
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
                onClick(
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
                    resumeViewModel.apply {
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
                        if (resume.userName.isNotEmpty() ||
                            resume.schoolName.isNotEmpty() ||
                            resume.schoolName.isNotEmpty() ||
                            resume.programmingLanguage.isNotEmpty() ||
                            resume.companyName.isNotEmpty()
                        ) {
                            setIsResumeCreated(true)
                        }
                        Log.d(
                            "values: ",
                            " read : ${form.userName} ${resumeViewModel.resumeCreated.value}"
                        )
                    }
                }
           }
        }
    }

    private fun updateHomeScreenView(state:Boolean) {
        Log.d("values: ", "update Screen")
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

    override fun onPause() {
        super.onPause()
        showLog("lifeCycle","onPause")
    }

    override fun onDestroy() {
        super.onDestroy()
        showLog("lifecycle","onDestroy")
    }

}