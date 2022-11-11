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
import com.example.buildresume.databinding.FragmentEditProfileDetailsBinding
import com.example.buildresume.viewmodel.ResumeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditProfileDetailsFragment : Fragment() {

    private lateinit var binding: FragmentEditProfileDetailsBinding
    private val resumeViewModel: ResumeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditProfileDetailsBinding.inflate(inflater, container, false)

        ifEditedFillForm()
        binding.buttonSaveProfiledataEditProfileDetails.setOnClickListener {
            writeToLocal()
        }

        return binding.root
    }

    private fun writeToLocal() {
        binding.apply {
            if (!TextUtils.isEmpty(editTextEnterNameEditProfileDetails.text.toString()) &&
                !TextUtils.isEmpty(editTextUserMobileNumberEditProfileDetails.text.toString()) &&
                !TextUtils.isEmpty(editTextUserAddressEditProfileDetails.text.toString()) &&
                !TextUtils.isEmpty(editTextUserEmailEditProfileDetails.text.toString())
            ) {
                resumeViewModel.form.run {
                    userName = editTextEnterNameEditProfileDetails.text.toString()
                    userMobile = editTextUserMobileNumberEditProfileDetails.text.toString()
                    userAddress = editTextUserAddressEditProfileDetails.text.toString()
                    userEmail = editTextUserEmailEditProfileDetails.text.toString()
                    textViewFillFormEditProfileDetails.text = "data saved"
                }
                resumeViewModel.saveDataToLocal()
                showToast(requireContext(), R.string.data_saved)
            } else {
                showToast(requireContext(), R.string.empty_data)
            }
        }
    }

    private fun ifEditedFillForm() {
        binding.apply {
            resumeViewModel.form.run {
                editTextEnterNameEditProfileDetails.setText(userName)
                editTextUserMobileNumberEditProfileDetails.setText(userMobile)
                editTextUserAddressEditProfileDetails.setText(userAddress)
                editTextUserEmailEditProfileDetails.setText(userEmail)
            }
        }

    }

}
