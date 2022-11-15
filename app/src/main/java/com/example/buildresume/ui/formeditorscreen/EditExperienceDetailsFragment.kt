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
            resumeViewModel.run {
                form.run {
                    companyName = editTextEnterCompanyName.text.toString()
                    companyExperienceYear = editTextCompanyExperience.text.toString()
                    totalExperience = editTextTotalYearOfExperience.text.toString()
                }
                saveDataToLocal()
            }
            showToast(requireContext(), R.string.data_saved)
        }
    }

    private fun ifEditedFillForm() {
        binding.run {
            resumeViewModel.form.run {
                if (companyName.isNotEmpty()) {
                    textViewFillDetailsEditExperienceDetails.text =
                        getString(R.string.data_saved)
                }
                editTextEnterCompanyName.setText(companyName)
                editTextCompanyExperience.setText(companyExperienceYear)
                editTextTotalYearOfExperience.setText(totalExperience)
            }
        }
    }

}
