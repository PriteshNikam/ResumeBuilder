package com.example.buildresume.ui.formeditorscreen

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.buildresume.R
import com.example.buildresume.UtilClass.showToast
import com.example.buildresume.databinding.FragmentEditEducationDetailsBinding
import com.example.buildresume.viewmodel.ResumeViewModel
import kotlinx.coroutines.launch

class EditEducationDetailsFragment : Fragment() {

    private lateinit var binding: FragmentEditEducationDetailsBinding
    private val resumeViewModel: ResumeViewModel by activityViewModels()

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
        binding.apply {
            if (editTextSchoolNameEditEducationDetails.text.isNullOrEmpty().not() &&
                !TextUtils.isEmpty(editTextSchoolMarksEditEducationDetails.text.toString()) &&
                !TextUtils.isEmpty(editTextDegreeCollegeNameEditEducationDetails.text.toString()) &&
                !TextUtils.isEmpty(editTextDegreeMarksEditEducationDetails.text.toString())
            ) {
                resumeViewModel.form.run {
                    schoolName = editTextSchoolNameEditEducationDetails.text.toString()
                    schoolMarks = editTextSchoolMarksEditEducationDetails.text.toString()
                    collegeName = editTextCollegeNameEditEducationDetails.text.toString()
                    collegeMarks = editTextCollegeMarksEditEducationDetails.text.toString()
                    diplomaCollegeName =
                        editTextDiplomaCollegeNameEditEducationDetails.text.toString()
                    diplomaCollegeMarks = editTextDiplomaMarksEditEducationDetails.text.toString()
                    degreeCollegeName =
                        editTextDegreeCollegeNameEditEducationDetails.text.toString()
                    degreeMarks = editTextDegreeMarksEditEducationDetails.text.toString()
                }
                resumeViewModel.saveDataToLocal()
                showToast(requireContext(), R.string.data_saved)
            } else {
                showToast(requireContext(), R.string.empty_data)
            }
        }
    }

    private fun ifEditedFillForm() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                binding.apply {
                    resumeViewModel.apply {
                        resumeViewModel.readToLocal.collect { resume ->
                            if (resume.schoolName.isNotEmpty()) {
                                textViewFillEducationEditEducationDetails.text =
                                    getString(R.string.data_saved)
                            }
                            form.run {
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
            }
        }
    }

}