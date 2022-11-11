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
import com.example.buildresume.databinding.FragmentEditProjectDetailsBinding
import com.example.buildresume.viewmodel.ResumeViewModel
import kotlinx.coroutines.launch


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
        binding.apply {
            if (!TextUtils.isEmpty(editTextProjectTitle.text.toString()) &&
                !TextUtils.isEmpty(editTextProjectDescription.text.toString())
            ) {
                resumeViewModel.form.run {
                    projectTitle = editTextProjectTitle.text.toString()
                    projectDescription = editTextProjectDescription.text.toString()
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
                resumeViewModel.apply {
                    readToLocal.collect() { resume ->
                        if (resume.programmingLanguage.isNotEmpty()) {
                            binding.textViewFillFormEditProjectDetails.text =
                                getString(R.string.data_saved)
                        }
                        binding.apply {
                            form.run {
                                editTextProjectTitle.setText(projectTitle)
                                editTextProjectDescription.setText(projectDescription)
                            }
                        }
                    }
                }
            }
        }
    }
}