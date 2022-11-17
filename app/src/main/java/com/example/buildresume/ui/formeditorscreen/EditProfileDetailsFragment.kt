package com.example.buildresume.ui.formeditorscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.buildresume.R
import com.example.buildresume.UtilClass.showToast
import com.example.buildresume.databinding.FragmentEditProfileDetailsBinding
import com.example.buildresume.viewmodel.ResumeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditProfileDetailsFragment : Fragment() {

    private lateinit var binding: FragmentEditProfileDetailsBinding
    private val resumeViewModel: ResumeViewModel by activityViewModels()

    private var isDataStored: Boolean = false

    private val currentTime: Long by lazy{ System.currentTimeMillis() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditProfileDetailsBinding.inflate(inflater, container, false)

        // val viewPager = activity?.findViewById<ViewPager2>(R.id.viewpager_formSlide)

        ifEditedFillForm()

        binding.buttonSaveProfileDataEditProfileDetails.setOnClickListener {
            writeToLocal()
        }
        return binding.root
    }

    private fun writeToLocal() {
        binding.run {
            if (editTextEnterNameEditProfileDetails.text.isNullOrEmpty().not() &&
                editTextUserMobileNumberEditProfileDetails.text.isNullOrEmpty().not() &&
                editTextUserAddressEditProfileDetails.text.isNullOrEmpty().not() &&
                editTextUserEmailEditProfileDetails.text.isNullOrEmpty().not()
            ) {
                resumeViewModel.run {
                    binding.run {
                        resumeViewModel.resume.run {
                            userName = editTextEnterNameEditProfileDetails.text.toString()
                            userMobile = editTextUserMobileNumberEditProfileDetails.text.toString()
                            userAddress = editTextUserAddressEditProfileDetails.text.toString()
                            userEmail = editTextUserEmailEditProfileDetails.text.toString()
                            resumeTime = currentTime.toString()
                        }
                    }
                    if (isDataStored) { // logic need to try for multiple test cases.
                        if (resume.resumeId == 0) {
                            resume.resumeId = allResumeList.value!!.first().resumeId // first because all_List reversed in descending order.
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
               if(editTextEnterNameEditProfileDetails.text.isNullOrEmpty()) editTextEnterNameEditProfileDetails.error = ""
               if(editTextUserMobileNumberEditProfileDetails.text.isNullOrEmpty())   editTextUserMobileNumberEditProfileDetails.error = ""
                 if(  editTextUserAddressEditProfileDetails.text.isNullOrEmpty()) editTextUserAddressEditProfileDetails.error = ""
                    if(    editTextUserEmailEditProfileDetails.text.isNullOrEmpty()) editTextUserEmailEditProfileDetails.error = ""
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
                editTextEnterNameEditProfileDetails.setText(userName)
                editTextUserMobileNumberEditProfileDetails.setText(userMobile)
                editTextUserAddressEditProfileDetails.setText(userAddress)
                editTextUserEmailEditProfileDetails.setText(userEmail)
            }
        }
    }
}