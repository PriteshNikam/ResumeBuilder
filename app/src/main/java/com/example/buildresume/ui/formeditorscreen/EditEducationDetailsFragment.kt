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

    private val currentTime: Long by lazy{ System.currentTimeMillis() }

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
                    binding.run {
                        resumeViewModel.run {
                            resume.run {
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
                if (editTextSchoolNameEditEducationDetails.text.isNullOrEmpty()) editTextSchoolNameEditEducationDetails.error =
                    ""
                if (editTextSchoolMarksEditEducationDetails.text.isNullOrEmpty()) editTextSchoolMarksEditEducationDetails.error =
                    ""
                if (editTextDegreeCollegeNameEditEducationDetails.text.isNullOrEmpty()) editTextDegreeCollegeNameEditEducationDetails.error =
                    ""
                if (editTextDegreeMarksEditEducationDetails.text.isNullOrEmpty()) editTextDegreeMarksEditEducationDetails.error =
                    ""
                showToast(requireContext(), R.string.empty_data)
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