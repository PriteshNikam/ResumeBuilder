package com.example.buildresume.ui.formeditorscreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.buildresume.R
import com.example.buildresume.UtilClass.showToast
import com.example.buildresume.databinding.FragmentEditExperienceDetailsBinding
import com.example.buildresume.viewmodel.ResumeViewModel
import kotlinx.coroutines.launch


class EditExperienceDetailsFragment : Fragment() {

    private lateinit var binding: FragmentEditExperienceDetailsBinding
    private val resumeViewModel: ResumeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditExperienceDetailsBinding.inflate(inflater,container,false)

        ifEditedFillForm()
        binding.buttonSaveExperienceDetails.setOnClickListener {
            writeToLocal()
        }
        return binding.root
    }

    private fun writeToLocal() {
        binding.apply {
                resumeViewModel.form.run{
                    companyName = editTextEnterCompanyName.text.toString()
                    companyExperienceYear = editTextCompanyExperience.text.toString()
                    totalExperience = editTextTotalYearOfExperience.text.toString()
                }
                resumeViewModel.saveDataToLocal()
                showToast(requireContext(),R.string.data_saved)
        }
    }

    private fun ifEditedFillForm() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                binding.apply {
                    resumeViewModel.apply {
                       readToLocal.collect() { resume ->
                            if (resume.companyName.isNotEmpty()) {
                                textViewFillDetailsEditExperienceDetails.text =
                                    getString(R.string.data_saved)
                            }
                            form.run {
                                editTextEnterCompanyName.setText(companyName)
                                editTextCompanyExperience.setText(companyExperienceYear)
                                editTextTotalYearOfExperience.setText(totalExperience)
                            }
                        }
                    }
                }
            }
        }
    }

}