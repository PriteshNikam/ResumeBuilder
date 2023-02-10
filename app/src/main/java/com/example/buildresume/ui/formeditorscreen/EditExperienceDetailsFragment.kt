package com.example.buildresume.ui.formeditorscreen

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

@AndroidEntryPoint
class EditExperienceDetailsFragment : Fragment() {

    private lateinit var binding: FragmentEditExperienceDetailsBinding
    private val resumeViewModel: ResumeViewModel by activityViewModels()

    private val currentTime: Long by lazy { System.currentTimeMillis() }

    private var isDataStored: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditExperienceDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ifEditedFillForm()
        binding.buttonSaveExperienceDetails.setOnClickListener {
            saveExperienceDetails()
        }
    }

    private fun saveExperienceDetails() {
        binding.run {
            resumeViewModel.run {
                resume.run {
                    if (isExperienceFormFilled()) {
                        updateResumeObjectExperience()
                        if (isDataStored) {
                            if (resumeId == 0) {
                                resumeId =
                                    allResumeList.value!!.first().resumeId
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
                        if (editTextEnterCompanyName.text.isNullOrEmpty().not() ||
                            editTextCompanyExperience.text.isNullOrEmpty().not() ||
                            editTextTotalYearOfExperience.text.isNullOrEmpty().not()
                        ) {
                            submitEmptyDetailsError()
                        } else {
                            if (companyName.isEmpty().not() &&
                                companyExperienceYear.isEmpty().not() &&
                                totalExperience.isEmpty().not()
                            ) {
                                updateResumeObjectExperience()
                                updateResumeInLocal(resume)
                                showToast(requireContext(), R.string.removed_experience_details)
                            } else {
                                showToast(requireContext(), R.string.fill_details)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun submitEmptyDetailsError() {
        binding.run {
            if (editTextEnterCompanyName.text.isNullOrEmpty())
                editTextEnterCompanyName.error = ""
            if (editTextCompanyExperience.text.isNullOrEmpty())
                editTextCompanyExperience.error = ""
            if (editTextTotalYearOfExperience.text.isNullOrEmpty())
                editTextTotalYearOfExperience.error = ""
        }
    }

    private fun updateResumeObjectExperience() {
        binding.run {
            resumeViewModel.resume.run {
                companyName = editTextEnterCompanyName.text.toString()
                companyExperienceYear = editTextCompanyExperience.text.toString()
                totalExperience = editTextTotalYearOfExperience.text.toString()
                resumeTime = currentTime.toString()
            }
        }
    }

    private fun isExperienceFormFilled(): Boolean {
        binding.run {
            return editTextEnterCompanyName.text.isNullOrEmpty().not() &&
                    editTextCompanyExperience.text.isNullOrEmpty().not() &&
                    editTextTotalYearOfExperience.text.isNullOrEmpty().not()
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
