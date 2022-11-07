package com.example.buildresume.ui.formeditorscreen

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.buildresume.databinding.FragmentEditEducationDetailsBinding
import com.example.buildresume.viewmodel.ResumeViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch


class EditEducationDetailsFragment : Fragment() {

    private lateinit var binding :FragmentEditEducationDetailsBinding

    private val resumeViewModel: ResumeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentEditEducationDetailsBinding.inflate(inflater,container,false)

        defaultFormFill()

        binding.buttonSaveEducationDetailsEditEducationDetails.setOnClickListener {
            writeToLocal()
        }

        return binding.root
    }

    private fun writeToLocal() {
        binding.apply {
            if (!TextUtils.isEmpty(editTextSchoolNameEditEducationDetails.text.toString()) &&
                !TextUtils.isEmpty(editTextSchoolMarksEditEducationDetails.text.toString()) &&
                !TextUtils.isEmpty(editTextCollegeNameEditEducationDetails.text.toString()) &&
                !TextUtils.isEmpty(editTextCollegeMarksEditEducationDetails.text.toString()) &&
                !TextUtils.isEmpty(editTextDiplomaCollegeNameEditEducationDetails.text.toString()) &&
                !TextUtils.isEmpty(editTextDiplomaMarksEditEducationDetails.text.toString()) &&
            !TextUtils.isEmpty(editTextDegreeCollegeNameEditEducationDetails.text.toString()) &&
            !TextUtils.isEmpty(editTextDegreeMarksEditEducationDetails.text.toString())) {

                resumeViewModel.form.run {
                    schoolName = editTextSchoolNameEditEducationDetails.text.toString()
                    schoolMarks =  editTextSchoolMarksEditEducationDetails.text.toString()
                    collegeName = editTextCollegeNameEditEducationDetails.text.toString()
                    collegeMarks = editTextCollegeMarksEditEducationDetails.text.toString()
                    diplomaCollegeName = editTextDiplomaCollegeNameEditEducationDetails.text.toString()
                    diplomaCollegeMarks = editTextDiplomaMarksEditEducationDetails.text.toString()
                    degreeCollegeName = editTextDegreeCollegeNameEditEducationDetails.text.toString()
                    degreeMarks = editTextDegreeMarksEditEducationDetails.text.toString()

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
                    if(resume.schoolName.isNotEmpty()) {
                        binding.textViewFillEducationEditEducationDetails.text = "data saved"
                    }
                    binding.apply {
                        resumeViewModel.form.run {
                            editTextSchoolNameEditEducationDetails.setText(schoolName)
                            editTextSchoolMarksEditEducationDetails.setText(schoolMarks)
                            editTextCollegeNameEditEducationDetails.setText(collegeName)
                            editTextCollegeMarksEditEducationDetails.setText(collegeMarks)
                            editTextDiplomaCollegeNameEditEducationDetails.setText(diplomaCollegeName)
                            editTextDiplomaMarksEditEducationDetails.setText(diplomaCollegeMarks)
                            editTextDegreeCollegeNameEditEducationDetails.setText(degreeCollegeName)
                            editTextDegreeMarksEditEducationDetails.setText(degreeMarks)
                        }
                    }


                }
            }
        }
    }
    private fun createToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
    override fun onPause() {
        super.onPause()
       // viewLifecycleOwner.lifecycleScope.cancel()
    }

}