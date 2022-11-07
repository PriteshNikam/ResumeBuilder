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
import com.example.generatepdf.GeneratePdf
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
            resumeViewModel.generatePdf(requireContext())
        }

        return  binding.root
    }
    private fun createToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun readStoredData(){
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                resumeViewModel.readToLocal.collect() { resume ->
                    createToast(resume.userName)
                    Log.d(
                        "saved_data",
                        "${resume.userName} ${resume.userMobile} ${resume.userEmail} ${resume.userAddress}"
                    )
                    resumeViewModel.apply {
                        resume.run {
                            form.userName = userName
                            form.userMobile = userMobile
                            form.userAddress = userAddress
                            form.userEmail = userEmail
                            form.schoolName = schoolName
                            form.schoolMarks = schoolMarks
                            form.collegeName = collegeName
                            form.collegeMarks = collegeMarks
                            form.diplomaCollegeName = diplomaCollegeName
                            form.diplomaCollegeMarks = diplomaCollegeMarks
                            form.degreeCollegeName = degreeCollegeName
                            form.degreeMarks = degreeMarks
                            form.programmingLanguage = programmingLanguage
                            form.softwareTools = softwareTools
                            form.certification = certification
                            form.otherSkills = otherSkills
                        }
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