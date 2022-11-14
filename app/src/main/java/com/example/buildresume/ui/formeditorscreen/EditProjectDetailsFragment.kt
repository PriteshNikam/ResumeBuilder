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
import com.example.buildresume.databinding.FragmentEditProjectDetailsBinding
import com.example.buildresume.viewmodel.ResumeViewModel

class EditProjectDetailsFragment : Fragment() {

    private lateinit var binding: FragmentEditProjectDetailsBinding
    private val resumeViewModel: ResumeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditProjectDetailsBinding.inflate(inflater, container, false)

        ifEditedFillForm()
        binding.buttonSaveProjectDetails.setOnClickListener {
            writeToLocal()
        }
        return binding.root
    }

    private fun writeToLocal() {
        binding.run {
            if (!TextUtils.isEmpty(editTextProjectTitle.text.toString()) &&
                !TextUtils.isEmpty(editTextProjectDescription.text.toString())
            ) {
                resumeViewModel.run {
                    form.run {
                        projectTitle = editTextProjectTitle.text.toString()
                        projectDescription = editTextProjectDescription.text.toString()
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
                    textViewFillFormEditProjectDetails.text =
                        getString(R.string.data_saved)
                }
                editTextProjectTitle.setText(projectTitle)
                editTextProjectDescription.setText(projectDescription)
            }
        }
    }
}

