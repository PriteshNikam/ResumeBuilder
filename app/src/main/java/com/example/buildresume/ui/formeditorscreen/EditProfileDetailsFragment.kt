package com.example.buildresume.ui.formeditorscreen

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.buildresume.R
import com.example.buildresume.UtilClass.showToast
import com.example.buildresume.databinding.FragmentEditProfileDetailsBinding
import com.example.buildresume.viewmodel.ResumeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EditProfileDetailsFragment : Fragment() {

    private lateinit var binding: FragmentEditProfileDetailsBinding

    private val resumeViewModel: ResumeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentEditProfileDetailsBinding.inflate(inflater, container, false)

        defaultFormFill()

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
                }
                resumeViewModel.saveDataToLocal()
                showToast(requireContext(), R.string.data_saved)
            } else {
                showToast(requireContext(), R.string.empty_data)
            }
        }
    }

    private fun defaultFormFill() {
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
