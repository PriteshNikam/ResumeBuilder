package com.example.buildresume.ui.formeditorscreen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.buildresume.R
import com.example.buildresume.UtilClass.showToast
import com.example.buildresume.databinding.FragmentEditEducationDetailsBinding
import com.example.buildresume.viewmodel.ResumeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditEducationDetailsFragment : Fragment() {

    private lateinit var binding: FragmentEditEducationDetailsBinding
    private val resumeViewModel: ResumeViewModel by activityViewModels()

    private var isDataStored: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditEducationDetailsBinding.inflate(inflater, container, false)

        ifEditedFillForm()
        binding.buttonSaveEducationDetailsEditEducationDetails.setOnClickListener {
            writeToLocal()
        }
        return binding.root
    }

    private fun writeToLocal() {
        binding.run {
            if (editTextSchoolNameEditEducationDetails.text.isNullOrEmpty().not() &&
                editTextSchoolMarksEditEducationDetails.text.isNullOrEmpty().not() &&
                editTextDegreeCollegeNameEditEducationDetails.text.isNullOrEmpty().not() &&
                editTextDegreeMarksEditEducationDetails.text.isNullOrEmpty().not()
            ) {
                resumeViewModel.run {
                    resume.run {
                        schoolName = editTextSchoolNameEditEducationDetails.text.toString()
                        schoolMarks = editTextSchoolMarksEditEducationDetails.text.toString()
                        collegeName = editTextCollegeNameEditEducationDetails.text.toString()
                        collegeMarks = editTextCollegeMarksEditEducationDetails.text.toString()
                        diplomaCollegeName =
                            editTextDiplomaCollegeNameEditEducationDetails.text.toString()
                        diplomaCollegeMarks =
                            editTextDiplomaMarksEditEducationDetails.text.toString()
                        degreeCollegeName =
                            editTextDegreeCollegeNameEditEducationDetails.text.toString()
                        degreeMarks = editTextDegreeMarksEditEducationDetails.text.toString()
                    }
                    if (isDataStored) {
                        updateResume(resume)
                        showToast(requireContext(), R.string.data_updated)
                    } else {
                        insertResume()
                        showToast(requireContext(), R.string.data_saved)
                    }
                }
            } else {
                showToast(requireContext(), R.string.empty_data)
            }
        }
    }

    private fun ifEditedFillForm() {
        binding.run {
            resumeViewModel.resume.run {
                if (isFormFilled()) {
                    Log.d("education isFormFilled","${isFormFilled()}")
                    isDataStored = true
                    textViewFillEducationEditEducationDetails.text =
                        getString(R.string.data_saved)
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