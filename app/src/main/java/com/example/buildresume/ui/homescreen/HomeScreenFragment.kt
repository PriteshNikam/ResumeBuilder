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

    var isItemSelected = false

/*    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if(isItemSelected) {
                readStoredData()
                updateHomeScreenView()
                binding.singleResumeViewConstraint.setBackgroundResource(R.drawable.custom_view_shape)
                showLog("value: ", resumeViewModel.form.userName)
                binding.textViewResumeUserNameHomeScreen.text = resumeViewModel.form.userName
                binding.imageVIewResumeDocumentView.visibility = View.VISIBLE
                binding.imageViewDeleteResume.visibility = View.GONE
                isItemSelected = false
            }
            else {
                activity?.finish()
            }
        }
    }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentArgs = HomeScreenFragmentArgs.fromBundle(requireArguments())
       // requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
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

        updateHomeScreenView()

        binding.singleResumeViewConstraint.setOnClickListener{
            onClick(view,HomeScreenFragmentDirections.actionHomeScreenFragmentToFormEditorScreenFragment())
        }

        binding.apply {
            singleResumeViewConstraint.setOnLongClickListener {
                isItemSelected = true
                singleResumeViewConstraint.setBackgroundResource(R.drawable.delete_resume_shape)
                textViewResumeUserNameHomeScreen.visibility = View.GONE
                imageVIewResumeDocumentView.visibility = View.GONE
                imageViewDeleteResume.visibility = View.VISIBLE
                singleResumeViewConstraint.setOnClickListener {
                    resumeViewModel.deleteLocalData()
                    readStoredData()
                    resumeViewModel.setIsResumeCreated(false)
                    updateHomeScreenView()
                    imageViewDeleteResume.visibility = View.GONE
                    binding.textViewUserNameHomeScreen.text = ""
                    isItemSelected = false
                }
                return@setOnLongClickListener true
            }
            readStoredData()
            updateHomeScreenView()
        }

        binding.floatingButtonAddResumeHomeScreen.setOnClickListener{
            onClick(view,HomeScreenFragmentDirections.actionHomeScreenFragmentToFormEditorScreenFragment())
        }
    }

    private fun readStoredData() {
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
                        if (resume.userName.isNotEmpty()) {
                            setIsResumeCreated(true)
                        }
                        Log.d("values: "," read : ${form.userName} ${resumeViewModel.resumeCreated.value}")
                    }
                }
            }
        }
    }

    private fun updateHomeScreenView(){
        Log.d("values: ","update Screen")
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
    }


}