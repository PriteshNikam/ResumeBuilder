package com.example.buildresume.ui.formeditorscreen

import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import com.example.buildresume.databinding.FragmentFormEditorScreenBinding
import com.example.buildresume.viewmodel.ResumeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FormEditorScreenFragment : Fragment() {

    private lateinit var binding: FragmentFormEditorScreenBinding
    private lateinit var fragmentArgs: FormEditorScreenFragmentArgs
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var sharedPreferencesEditor: SharedPreferences.Editor

    private val resumeViewModel: ResumeViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fragmentArgs = FormEditorScreenFragmentArgs.fromBundle(requireArguments())
        sharedPreferences =
            requireActivity().getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
        sharedPreferencesEditor = sharedPreferences.edit()
        sharedPreferencesEditor.putString(storedResumeKey, fragmentArgs.userID.toString())
        sharedPreferencesEditor.commit()

        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
            PackageManager.PERMISSION_GRANTED
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFormEditorScreenBinding.inflate(inflater, container, false)

        Log.d("id", fragmentArgs.userID.toString()) // store uid to sharedPreference

        binding.buttonEditProfileFormEditorScreen.setOnClickListener {
            view?.findNavController()
                ?.navigate(FormEditorScreenFragmentDirections.actionFormEditorScreenFragmentToEditProfileDetailsFragment())
      }

        binding.buttonEditEducationFormEditorScreen.setOnClickListener{
            view?.findNavController()?.navigate(FormEditorScreenFragmentDirections.actionFormEditorScreenFragmentToEditEducationDetailsFragment())
        }

        binding.buttonEditSkillsFormEditorScreen.setOnClickListener{
            view?.findNavController()?.navigate(FormEditorScreenFragmentDirections.actionFormEditorScreenFragmentToEditSkillsDetailsFragment())
        }

        //binding.textViewFormEditorScreen.text = fragmentArgs.userID.toString()

        readStoredData()

        binding.buttonGeneratePdfFormEditorScreen.setOnClickListener{
            //resumeViewModel.generatePdf(requireContext())
            resumeViewModel.localGeneratePDF(requireContext())
        }

        return  binding.root
    }

    private fun readStoredData(){
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
                    }
                    resumeViewModel.form.run {
                        Log.d(
                            "education Form: ",
                            "$schoolName || $schoolMarks || $collegeName || $collegeMarks || $diplomaCollegeName || $diplomaCollegeMarks || $degreeCollegeName || $degreeMarks"
                        )
                    }
                }
            }
        }
    }
    companion object {
        private var storedResumeKey = "storedResume"
        private var PREF_FILE_NAME = "com.example.buildresume_preferences"
    }
}