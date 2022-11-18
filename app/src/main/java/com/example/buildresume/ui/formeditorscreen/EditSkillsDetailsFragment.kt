package com.example.buildresume.ui.formeditorscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.buildresume.R
import com.example.buildresume.UtilClass.showToast
import com.example.buildresume.databinding.FragmentEditSkillsDetailsBinding
import com.example.buildresume.viewmodel.ResumeViewModel

class EditSkillsDetailsFragment : Fragment() {

    private lateinit var binding: FragmentEditSkillsDetailsBinding
    private val resumeViewModel: ResumeViewModel by activityViewModels()
    private var isDataStored: Boolean = false
    private val currentTime: Long by lazy { System.currentTimeMillis() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditSkillsDetailsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ifEditedFillForm()
        binding.buttonSaveSkillsDetailsEditEducationDetails.setOnClickListener {
            saveSkillsDetails()
        }
    }
    private fun saveSkillsDetails() {
        binding.run {
            resumeViewModel.run {
                resume.run {
                    if (isSkillsFormFilled()) {
                        updateResumeObjectSkills()
                        if (isDataStored) {
                            if (resumeId == 0) {
                                resumeId = allResumeList.value!!.first().resumeId
                                updateResumeInLocal(resume)
                            }
                            updateResumeInLocal(resume)
                            showToast(requireContext(), R.string.data_updated)
                        } else {
                            isDataStored = true
                            insertResumeInLocal()
                            showToast(requireContext(), R.string.data_saved)
                        }

                    } else {
                        submitEmptyDetailsError()
                    }
                }
            }
        }
    }
    private fun submitEmptyDetailsError() {
        binding.run {
            if (editTextProgramLangEditSkillsDetails.text.isNullOrEmpty())
                editTextProgramLangEditSkillsDetails.error = ""
            if (editTextToolsEditSkillsDetails.text.isNullOrEmpty())
                editTextToolsEditSkillsDetails.error = ""
            if (editTextCertificatesEditSkillsDetails.text.isNullOrEmpty())
                editTextCertificatesEditSkillsDetails.error = ""
            if (editTextOtherSkillsEditSkillsDetails.text.isNullOrEmpty())
                editTextOtherSkillsEditSkillsDetails.error = ""
            showToast(requireContext(), R.string.empty_data)
        }
    }
    private fun updateResumeObjectSkills() {
        binding.run {
            resumeViewModel.resume.run {
                programmingLanguage = editTextProgramLangEditSkillsDetails.text.toString()
                softwareTools = editTextToolsEditSkillsDetails.text.toString()
                certification = editTextCertificatesEditSkillsDetails.text.toString()
                otherSkills = editTextOtherSkillsEditSkillsDetails.text.toString()
                resumeTime = currentTime.toString()
            }
        }
    }
    private fun isSkillsFormFilled(): Boolean {
        binding.run {
            return editTextProgramLangEditSkillsDetails.text.isNullOrEmpty().not() &&
                    editTextToolsEditSkillsDetails.text.isNullOrEmpty().not() &&
                    editTextCertificatesEditSkillsDetails.text.isNullOrEmpty().not() &&
                    editTextOtherSkillsEditSkillsDetails.text.isNullOrEmpty().not()
        }
    }
    private fun ifEditedFillForm() {
        binding.run {
            resumeViewModel.resume.run {
                if (isFormFilled()) {
                    isDataStored = true
                }
                editTextProgramLangEditSkillsDetails.setText(programmingLanguage)
                editTextToolsEditSkillsDetails.setText(softwareTools)
                editTextCertificatesEditSkillsDetails.setText(certification)
                editTextOtherSkillsEditSkillsDetails.setText(otherSkills)
            }
        }
    }
}


