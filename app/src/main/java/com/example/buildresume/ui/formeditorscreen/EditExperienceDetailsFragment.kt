package com.example.buildresume.ui.formeditorscreen

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.buildresume.R
import com.example.buildresume.UtilClass.showToast
import com.example.buildresume.databinding.FragmentEditExperienceDetailsBinding
import com.example.buildresume.viewmodel.ResumeViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class EditExperienceDetailsFragment : Fragment() {

    private lateinit var binding: FragmentEditExperienceDetailsBinding
    private val resumeViewModel: ResumeViewModel by activityViewModels()

    private val currentTime: Long by lazy{ System.currentTimeMillis() }

    private var isDataStored: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditExperienceDetailsBinding.inflate(inflater, container, false)

        ifEditedFillForm()
        binding.buttonSaveExperienceDetails.setOnClickListener {
            writeToLocal()
        }
        return binding.root
    }

    private fun writeToLocal() {
        binding.run {
            if (editTextEnterCompanyName.text.isNullOrEmpty().not() &&
                editTextCompanyExperience.text.isNullOrEmpty().not() &&
                editTextTotalYearOfExperience.text.isNullOrEmpty().not()
            ) {
                resumeViewModel.run {
                    resume.run {
                        companyName = editTextEnterCompanyName.text.toString()
                        companyExperienceYear = editTextCompanyExperience.text.toString()
                        totalExperience = editTextTotalYearOfExperience.text.toString()
                        resumeTime = currentTime.toString()
                    }
                    if (isDataStored) { // logic need to try for multiple test cases.
                        if (resume.resumeId == 0) {
                            resume.resumeId =
                                allResumeList.value!!.first().resumeId // 0 because all_List reversed in descending order.
                            updateResume(resume)
                        }
                        updateResume(resume)
                        showToast(requireContext(), R.string.data_updated)
                    } else {
                        isDataStored = true
                        insertResume()
                        showToast(requireContext(), R.string.data_saved)
                    }
                }
                showToast(requireContext(), R.string.data_saved)
            } else {
                if (editTextEnterCompanyName.text.isNullOrEmpty().not() ||
                    editTextCompanyExperience.text.isNullOrEmpty().not() ||
                    editTextTotalYearOfExperience.text.isNullOrEmpty().not()
                ) {
                    if (editTextEnterCompanyName.text.isNullOrEmpty()) editTextEnterCompanyName.error =
                        ""
                    if (editTextCompanyExperience.text.isNullOrEmpty()) editTextCompanyExperience.error =
                        ""
                    if (editTextTotalYearOfExperience.text.isNullOrEmpty()) editTextTotalYearOfExperience.error =
                        ""
                } else {
                    if(resumeViewModel.resume.companyName.isNullOrEmpty().not() &&
                        resumeViewModel.resume.companyExperienceYear.isNullOrEmpty().not() &&
                                resumeViewModel.resume.totalExperience.isNullOrEmpty().not()
                    ) {
                        resumeViewModel.run {
                            resume.run {
                                companyName = editTextEnterCompanyName.text.toString()
                                companyExperienceYear = editTextCompanyExperience.text.toString()
                                totalExperience = editTextTotalYearOfExperience.text.toString()
                            }
                            updateResume(resume)
                            showToast(requireContext(), R.string.removed_experience_details)
                        }
                    }else{
                        showToast(requireContext(),R.string.fill_details)
                    }
                }
            }
        }
    }

    private fun ifEditedFillForm() {
        binding.run {
            resumeViewModel.resume.run {
                if (isFormFilled()) {
                    isDataStored = true
                }
                editTextEnterCompanyName.setText(companyName)
                editTextCompanyExperience.setText(companyExperienceYear)
                editTextTotalYearOfExperience.setText(totalExperience)
            }
        }
    }




}
