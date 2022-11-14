package com.example.buildresume.ui.formeditorscreen

import android.os.Bundle
import android.text.TextUtils
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditSkillsDetailsBinding.inflate(layoutInflater, container, false)

        ifEditedFillForm()
        binding.buttonSaveSkillsDetailsEditEducationDetails.setOnClickListener {
            writeToLocal()
        }
        return binding.root
    }

    private fun writeToLocal() {
        binding.run {
            if (!TextUtils.isEmpty(editTextProgramLangEditSkillsDetails.text.toString()) &&
                !TextUtils.isEmpty(editTextToolsEditSkillsDetails.text.toString()) &&
                !TextUtils.isEmpty(editTextCertificatesEditSkillsDetails.text.toString()) &&
                !TextUtils.isEmpty(editTextOtherSkillsEditSkillsDetails.text.toString())
            ) {
                resumeViewModel.run {
                    form.run {
                        programmingLanguage = editTextProgramLangEditSkillsDetails.text.toString()
                        softwareTools = editTextToolsEditSkillsDetails.text.toString()
                        certification = editTextCertificatesEditSkillsDetails.text.toString()
                        otherSkills = editTextOtherSkillsEditSkillsDetails.text.toString()
                    }
                    saveDataToLocal()
                    showToast(requireContext(), R.string.data_saved)
                }
            } else {
                showToast(requireContext(), R.string.empty_data)
            }
        }
    }

    private fun ifEditedFillForm() {
        binding.run {
            resumeViewModel.form.run {
                if (programmingLanguage.isNotEmpty()) {
                    textViewFillSkillsEditSkillsDetails.text = getString(R.string.data_saved)
                }
                    editTextProgramLangEditSkillsDetails.setText(programmingLanguage)
                    editTextToolsEditSkillsDetails.setText(softwareTools)
                    editTextCertificatesEditSkillsDetails.setText(certification)
                    editTextOtherSkillsEditSkillsDetails.setText(otherSkills)
                }
        }
    }
}


