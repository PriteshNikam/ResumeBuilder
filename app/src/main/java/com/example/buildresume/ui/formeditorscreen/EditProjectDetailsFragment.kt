package com.example.buildresume.ui.formeditorscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.buildresume.R
import com.example.buildresume.UtilClass.showToast
import com.example.buildresume.databinding.FragmentEditProjectDetailsBinding
import com.example.buildresume.viewmodel.ResumeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditProjectDetailsFragment : Fragment() {

    private lateinit var binding: FragmentEditProjectDetailsBinding
    private val resumeViewModel: ResumeViewModel by activityViewModels()

    private var isDataStored: Boolean = false

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
            if (editTextProjectTitle.text.isNullOrEmpty().not() &&
                editTextProjectDescription.text.isNullOrEmpty().not()
            ) {
                resumeViewModel.run {
                    resume.run {
                        projectTitle = editTextProjectTitle.text.toString()
                        projectDescription = editTextProjectDescription.text.toString()
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
            } else {
                if (editTextProjectTitle.text.isNullOrEmpty().not() ||
                    editTextProjectDescription.text.isNullOrEmpty().not()
                ) {
                    if (editTextProjectTitle.text.isNullOrEmpty()) editTextProjectTitle.error =
                        ""
                    if (editTextProjectDescription.text.isNullOrEmpty()) editTextProjectDescription.error =
                        ""
                } else {
                    if (resumeViewModel.resume.projectTitle.isEmpty().not() &&
                        resumeViewModel.resume.projectDescription.isEmpty().not()
                    ) {
                        resumeViewModel.run {
                            resume.run {
                                projectTitle = editTextProjectTitle.text.toString()
                                projectDescription = editTextProjectDescription.text.toString()
                            }
                            updateResume(resume)
                            showToast(requireContext(), R.string.remove_project_details)
                        }
                    } else{
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
                    textViewFillFormEditProjectDetails.text =
                        getString(R.string.data_saved)
                }
                editTextProjectTitle.setText(projectTitle)
                editTextProjectDescription.setText(projectDescription)
            }
        }
    }
}



