package com.example.buildresume.ui.formeditorscreen

import android.location.Address
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.buildresume.databinding.FragmentEditProfileDetailsBinding
import com.example.buildresume.viewmodel.ResumeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EditProfileDetailsFragment : Fragment() {

    private lateinit var binding: FragmentEditProfileDetailsBinding

    private val resumeViewModel: ResumeViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentEditProfileDetailsBinding.inflate(inflater, container, false)

        defaultFormFill()

        binding.buttonSaveProfiledataEditProfileDetails.setOnClickListener {
            writeToLocal()
        }

        return binding.root
    }

    private fun writeToLocal() {
        binding.apply {
            if (!TextUtils.isEmpty(editTextEnterNameEditProfileDetails.text.toString()) &&
                !TextUtils.isEmpty(editTextUserMobileNumberEditProfileDetails.text.toString()) &&
                !TextUtils.isEmpty(editTextUserAddressEditProfileDetails.text.toString()) &&
                !TextUtils.isEmpty(editTextUserEmailEditProfileDetails.text.toString())) {

                resumeViewModel.writeToLocal(editTextEnterNameEditProfileDetails.text.toString(),
                    editTextUserMobileNumberEditProfileDetails.text.toString(),
                    editTextUserAddressEditProfileDetails.text.toString(),
                    editTextUserEmailEditProfileDetails.text.toString(),

                    resumeViewModel.readSchoolName.value.toString(),
                    resumeViewModel.readSchoolMarks.value.toString(),
                    resumeViewModel.readCollegeName.value.toString(),
                    resumeViewModel.readCollegeMarks.value.toString(),
                    resumeViewModel.readDiplomaCollegeName.value.toString(),
                    resumeViewModel.readDiplomaCollegeMarks.value.toString(),
                    resumeViewModel.readDegreeCollegeName.value.toString(),
                    resumeViewModel.readDegreeMarks.value.toString(),

                    resumeViewModel.readProgrammingLanguage.value.toString(),
                    resumeViewModel.readSoftwareTools.value.toString(),
                    resumeViewModel.readCertification.value.toString(),
                    resumeViewModel.readOtherSkills.value.toString())
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
                    if(resume.userName.isNotEmpty()) {
                        binding.textViewFillFormEditProfileDetails.text = "data saved"
                    }
                    Log.d(
                        "saved_data_profile",
                        "${resume.userName} ${resume.userMobile} ${resume.userEmail} ${resume.userAddress}"
                    )
                    binding.apply {
                        editTextEnterNameEditProfileDetails.setText(resume.userName)
                        editTextUserMobileNumberEditProfileDetails.setText(resume.userMobile)
                        editTextUserAddressEditProfileDetails.setText(resume.userAddress)
                        editTextUserEmailEditProfileDetails.setText(resume.userEmail)
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
