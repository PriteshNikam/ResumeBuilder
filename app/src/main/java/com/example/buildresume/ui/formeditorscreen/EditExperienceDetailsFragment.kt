package com.example.buildresume.ui.formeditorscreen

import android.os.Bundle
import android.text.TextUtils
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
import com.example.buildresume.databinding.FragmentEditExperienceDetailsBinding
import com.example.buildresume.databinding.FragmentEditProjectDetailsBinding
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

        defaultFormFill()

        binding.buttonSaveExperienceDetails.setOnClickListener {
            writeToLocal()
        }

        return binding.root
    }

    private fun writeToLocal() {
        binding.apply {
            if (!TextUtils.isEmpty(editTextEnterCompanyName.text.toString()) &&
                !TextUtils.isEmpty(editTextCompanyExperience.text.toString()) &&
                !TextUtils.isEmpty(editTextTotalYearOfExperience.text.toString())){

                resumeViewModel.form.run{
                    companyName = editTextEnterCompanyName.text.toString()
                    companyExperienceYear = editTextCompanyExperience.text.toString()
                    totalExperience = editTextTotalYearOfExperience.text.toString()

                }
                resumeViewModel.saveDataToLocal()
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
                        binding.textViewFillDetailsEditExperienceDetails.text = getString(R.string.data_stored)
                    }
                    binding.apply {
                        resumeViewModel.form.run {
                            editTextEnterCompanyName.setText(companyName)
                            editTextCompanyExperience.setText(companyExperienceYear)
                            editTextTotalYearOfExperience.setText(totalExperience)
                        }
                    }

                }
            }
        }
    }
    private fun createToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }


}