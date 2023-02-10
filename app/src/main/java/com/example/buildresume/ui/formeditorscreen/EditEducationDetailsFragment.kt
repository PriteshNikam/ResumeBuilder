package com.example.buildresume.ui.formeditorscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.buildresume.R
import com.example.buildresume.UtilClass.showToast
import com.example.buildresume.databinding.FragmentEditEducationDetailsBinding
import com.example.buildresume.viewmodel.ResumeViewModel

class EditEducationDetailsFragment : Fragment() {

    private lateinit var binding: FragmentEditEducationDetailsBinding
    private val resumeViewModel: ResumeViewModel by activityViewModels()
    private val currentTime: Long by lazy { System.currentTimeMillis() }
    private var isDataStored: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditEducationDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ifEditedFillForm()
        binding.buttonSaveEducationDetails.setOnClickListener {
            saveEducationDetails()
        }
    }
    private fun saveEducationDetails() {
        binding.run {
            resumeViewModel.run {
                resume.run {
                    if (isEducationFormFilled()) {
                        updateResumeObjectEducation()
                        if (isDataStored) {
                            if (resumeId == 0) {
                                resumeId = allResumeList.value!!.first().resumeId
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
                        submitEmptyDetailsError()
                    }
                }
            }
        }
    }
    private fun isEducationFormFilled(): Boolean {
        binding.run {
            return editTextSchoolNameEditEducationDetails.text.isNullOrEmpty().not() &&
                    editTextSchoolMarksEditEducationDetails.text.isNullOrEmpty().not() &&
                    editTextDegreeCollegeNameEditEducationDetails.text.isNullOrEmpty().not() &&
                    editTextDegreeMarksEditEducationDetails.text.isNullOrEmpty().not()
        }
    }
    private fun submitEmptyDetailsError() {
        binding.run {
            if (editTextSchoolNameEditEducationDetails.text.isNullOrEmpty())
                editTextSchoolNameEditEducationDetails.error = ""
            if (editTextSchoolMarksEditEducationDetails.text.isNullOrEmpty())
                editTextSchoolMarksEditEducationDetails.error = ""
            if (editTextDegreeCollegeNameEditEducationDetails.text.isNullOrEmpty())
                editTextDegreeCollegeNameEditEducationDetails.error = ""
            if (editTextDegreeMarksEditEducationDetails.text.isNullOrEmpty())
                editTextDegreeMarksEditEducationDetails.error = ""
            showToast(requireContext(), R.string.empty_data)
        }
    }
    private fun updateResumeObjectEducation() {
        binding.run {
            resumeViewModel.resume.run {
                schoolName = editTextSchoolNameEditEducationDetails.text.toString()
                schoolMarks =
                    editTextSchoolMarksEditEducationDetails.text.toString()
                collegeName =
                    editTextCollegeNameEditEducationDetails.text.toString()
                collegeMarks =
                    editTextCollegeMarksEditEducationDetails.text.toString()
                diplomaCollegeName =
                    editTextDiplomaCollegeNameEditEducationDetails.text.toString()
                diplomaCollegeMarks =
                    editTextDiplomaMarksEditEducationDetails.text.toString()
                degreeCollegeName =
                    editTextDegreeCollegeNameEditEducationDetails.text.toString()
                degreeMarks =
                    editTextDegreeMarksEditEducationDetails.text.toString()
                resumeTime = currentTime.toString()
            }
        }
    }
    private fun ifEditedFillForm() {
        binding.run {
            resumeViewModel.resume.run {
                if (isFormFilled()) {
                    isDataStored = true
                }
                editTextSchoolNameEditEducationDetails.setText(schoolName)
                editTextSchoolMarksEditEducationDetails.setText(schoolMarks)
                editTextCollegeNameEditEducationDetails.setText(collegeName)
                editTextCollegeMarksEditEducationDetails.setText(collegeMarks)
                editTextDiplomaCollegeNameEditEducationDetails.setText(
                    diplomaCollegeName
                )
                editTextDiplomaMarksEditEducationDetails.setText(diplomaCollegeMarks)
                editTextDegreeCollegeNameEditEducationDetails.setText(
                    degreeCollegeName
                )
                editTextDegreeMarksEditEducationDetails.setText(degreeMarks)
            }
        }
    }
}