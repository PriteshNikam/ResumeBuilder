package com.example.buildresume.ui.homescreen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.buildresume.UtilClass.onClick
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

    private var isResumeCreated = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentArgs =
            HomeScreenFragmentArgs.fromBundle(requireArguments())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeScreenBinding.inflate(inflater, container, false)
        binding.textViewUserNameHomeScreen.text = fragmentArgs.user?.displayName

        readStoredData()

        resumeViewModel.resumeCreated.observe(requireActivity(), Observer {
            if(it){
                binding.apply {
                    singleResumeViewConstraint.visibility = View.VISIBLE
                    floatingButtonAddResumeHomeScreen.visibility = View.GONE
                    textViewUserNameHomeScreen.text = resumeViewModel.form.userName
                    textViewResumeUserNameHomeScreen.text = resumeViewModel.form.userName
                    textViewEmptyTextHomeScreen.visibility = View.GONE
                }
            }else{
                binding.apply {
                    singleResumeViewConstraint.visibility = View.GONE
                    floatingButtonAddResumeHomeScreen.visibility = View.VISIBLE
                    textViewEmptyTextHomeScreen.visibility = View.VISIBLE
                }
            }
        })

        binding.singleResumeViewConstraint.setOnClickListener{
            onClick(view,HomeScreenFragmentDirections.actionHomeScreenFragmentToFormEditorScreenFragment())
        }

        binding.floatingButtonAddResumeHomeScreen.setOnClickListener{
            onClick(view,HomeScreenFragmentDirections.actionHomeScreenFragmentToFormEditorScreenFragment())
        }
        return binding.root
    }

    private fun readStoredData():Boolean {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
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
                        if(resume.userName.isNotEmpty()){
                            setIsResumeCreated()
                        }
                    }
                    resumeViewModel.form.run {
                        Log.d("readAllData","$userName || $userMobile || $userAddress || $userEmail || $schoolName || $schoolMarks || $collegeName ||" +
                                "$collegeMarks || $diplomaCollegeName || $diplomaCollegeMarks || $degreeCollegeName || $degreeMarks ||$programmingLanguage || $softwareTools" +
                                "$certification || $otherSkills || $projectTitle || $projectDescription || $companyName || $companyExperienceYear || $totalExperience")
                    }
                }
            }
        }
        return isResumeCreated
    }

}