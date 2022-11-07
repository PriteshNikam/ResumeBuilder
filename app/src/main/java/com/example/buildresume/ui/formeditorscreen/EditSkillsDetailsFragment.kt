package com.example.buildresume.ui.formeditorscreen

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.buildresume.R
import com.example.buildresume.databinding.FragmentEditSkillsDetailsBinding
import com.example.buildresume.viewmodel.ResumeViewModel
import kotlinx.coroutines.launch

class EditSkillsDetailsFragment : Fragment() {

    private lateinit var binding: FragmentEditSkillsDetailsBinding
    private val resumeViewModel: ResumeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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

                resumeViewModel.form.run{
                    programmingLanguage = editTextProgramLangEditSkillsDetails.text.toString()
                    softwareTools = editTextToolsEditSkillsDetails.text.toString()
                    certification = editTextCertificatesEditSkillsDetails.text.toString()
                    otherSkills = editTextOtherSkillsEditSkillsDetails.text.toString()
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
                        binding.textViewFillSkillsEditSkillsDetails.text = getString(R.string.data_stored)
                    }
                    binding.apply {
                        resumeViewModel.form.run {
                            editTextProgramLangEditSkillsDetails.setText(programmingLanguage)
                            editTextToolsEditSkillsDetails.setText(softwareTools)
                            editTextCertificatesEditSkillsDetails.setText(certification)
                            editTextOtherSkillsEditSkillsDetails.setText(otherSkills)
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