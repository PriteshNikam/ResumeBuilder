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
    private val currentTime: Long by lazy { System.currentTimeMillis() }
    private var isDataStored: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditProjectDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ifEditedFillForm()
        binding.buttonSaveProjectDetails.setOnClickListener {
            saveProjectDetails()
        }
    }

    private fun saveProjectDetails() {
        binding.run {
            resumeViewModel.run {
                resume.run {
                    if (isProjectFormFilled()) {
                        updateResumeObjectProject()
                        if (isDataStored) { // logic need to try for multiple test cases.
                            if (resumeId == 0) {
                                resumeId =
                                    allResumeList.value!!.first().resumeId // 0 because all_List reversed in descending order.
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
                        if (editTextProjectTitle.text.isNullOrEmpty().not() ||
                            editTextProjectDescription.text.isNullOrEmpty().not()
                        ) {
                            submitEmptyDetails()
                        } else {
                            if (projectTitle.isEmpty().not() &&
                                projectDescription.isEmpty().not()
                            ) {
                                updateResumeObjectProject()
                                updateResumeInLocal(resume)
                                showToast(requireContext(), R.string.remove_project_details)
                            } else {
                                showToast(requireContext(), R.string.fill_details)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun submitEmptyDetails() {
        binding.run {
            if (editTextProjectTitle.text.isNullOrEmpty())
                editTextProjectTitle.error = ""
            if (editTextProjectDescription.text.isNullOrEmpty())
                editTextProjectDescription.error = ""
        }
    }

    private fun updateResumeObjectProject() {
        binding.run {
            resumeViewModel.resume.run {
                projectTitle = editTextProjectTitle.text.toString()
                projectDescription = editTextProjectDescription.text.toString()
                resumeTime = currentTime.toString()
            }
        }
    }

    private fun isProjectFormFilled(): Boolean {
        binding.run {
            return editTextProjectTitle.text.isNullOrEmpty().not() &&
                    editTextProjectDescription.text.isNullOrEmpty().not()
        }
    }

    private fun ifEditedFillForm() {
        binding.run {
            resumeViewModel.resume.run {
                if (isFormFilled()) {
                    isDataStored = true
                }
                editTextProjectTitle.setText(projectTitle)
                editTextProjectDescription.setText(projectDescription)
            }
        }
    }
}



