package com.example.buildresume.ui.formeditorscreen

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.buildresume.R
import com.example.buildresume.databinding.FragmentEditEducationDetailsBinding
import com.example.buildresume.databinding.FragmentEditProfileDetailsBinding
import com.example.buildresume.databinding.FragmentEditSkillsDetailsBinding
import com.example.buildresume.viewmodel.ResumeViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class EditSkillsDetailsFragment : Fragment() {

    private lateinit var binding: FragmentEditSkillsDetailsBinding
    private val resumeViewModel: ResumeViewModel by activityViewModels()
    

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentEditSkillsDetailsBinding.inflate(layoutInflater,container,false)

        defaultFormFill()

        binding.buttonSaveSkillsDetailsEditEducationDetails.setOnClickListener {
            writeToLocal()
        }

        return binding.root
     }

    private fun writeToLocal() {
        binding.apply {
            if (!TextUtils.isEmpty(editTextProgramLangEditSkillsDetails.text.toString()) &&
                !TextUtils.isEmpty(editTextToolsEditSkillsDetails.text.toString()) &&
                !TextUtils.isEmpty(editTextCertificatesEditSkillsDetails.text.toString()) &&
                !TextUtils.isEmpty(editTextOtherSkillsEditSkillsDetails.text.toString())) {

                resumeViewModel.writeToLocal(resumeViewModel.readUserName.value.toString(),
                    resumeViewModel.readUserMobile.value.toString(),
                    resumeViewModel.readUserAddress.value.toString(),
                    resumeViewModel.readUserEmail.value.toString(),

                    resumeViewModel.readSchoolName.value.toString(),
                    resumeViewModel.readSchoolMarks.value.toString(),
                    resumeViewModel.readCollegeName.value.toString(),
                    resumeViewModel.readCollegeMarks.value.toString(),
                    resumeViewModel.readDiplomaCollegeName.value.toString(),
                    resumeViewModel.readDiplomaCollegeMarks.value.toString(),
                    resumeViewModel.readDegreeCollegeName.value.toString(),
                    resumeViewModel.readDegreeMarks.value.toString(),

                    editTextProgramLangEditSkillsDetails.text.toString(),
                    editTextToolsEditSkillsDetails.text.toString(),
                    editTextCertificatesEditSkillsDetails.text.toString(),
                    editTextOtherSkillsEditSkillsDetails.text.toString())
                createToast("data saved")
            } else {
                Toast.makeText(requireContext(), "empty data", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun defaultFormFill() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                resumeViewModel.readToLocal.collect() { resume ->
                    if(resume.programmingLanguage.isNotEmpty()) {
                        binding.textViewFillSkillsEditSkillsDetails.text = "data saved"
                    }
                    Log.d(
                        "saved_data_Skills",
                        "${resume.userName} ${resume.userMobile} ${resume.userEmail} ${resume.userAddress}"
                    )
                    binding.apply {
                        editTextProgramLangEditSkillsDetails.setText(resume.programmingLanguage)
                        editTextToolsEditSkillsDetails.setText(resume.softwareTools)
                        editTextCertificatesEditSkillsDetails.setText(resume.certification)
                        editTextOtherSkillsEditSkillsDetails.setText(resume.otherSkills)
                    }

                    resumeViewModel.setUserName(resume.userName)
                    resumeViewModel.setUserMobile(resume.userMobile)
                    resumeViewModel.setUserAddress(resume.userAddress)
                    resumeViewModel.setUserEmail(resume.userEmail)
                    resumeViewModel.setSchoolName(resume.schoolName)
                    resumeViewModel.setSchoolMarks(resume.schoolMarks)
                    resumeViewModel.setCollegeName(resume.collegeName)
                    resumeViewModel.setCollegeMarks(resume.collegeMarks)
                    resumeViewModel.setCollegeName(resume.collegeName)
                    resumeViewModel.setCollegeMarks(resume.collegeMarks)
                    resumeViewModel.setDiplomaCollegeName(resume.diplomaCollegeName)
                    resumeViewModel.setDiplomaMarks(resume.diplomaCollegeMarks)
                    resumeViewModel.setDegreeCollegeName(resume.degreeCollegeName)
                    resumeViewModel.setDegreeMarks(resume.degreeMarks)
                    resumeViewModel.setDegreeMarks(resume.degreeMarks)
                    resumeViewModel.setProgrammingLanguage(resume.programmingLanguage)
                    resumeViewModel.setSoftwareTools(resume.softwareTools)
                    resumeViewModel.setCertification(resume.certification)
                    resumeViewModel.setOtherSkills(resume.otherSkills)

                }
            }
        }
    }
    private fun createToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onPause() {
        super.onPause()
        viewLifecycleOwner.lifecycleScope.cancel()
    }

}