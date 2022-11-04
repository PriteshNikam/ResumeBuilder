package com.example.buildresume.ui.formeditorscreen

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.ContentInfo
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.buildresume.databinding.FragmentEditProfileDetailsBinding
import com.example.buildresume.viewmodel.ResumeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@AndroidEntryPoint
class EditProfileDetailsFragment : Fragment() {

    private lateinit var binding: FragmentEditProfileDetailsBinding

    private val resumeViewModel: ResumeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("lifeCycle", "onCreate")


    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("lifeCycle", "onCreateView")

        // Inflate the layout for this fragment
        binding = FragmentEditProfileDetailsBinding.inflate(inflater, container, false)

        defaultFormFill()

        binding.buttonSaveProfiledataEditProfileDetails.setOnClickListener {
            writeToLocal()
            Toast.makeText(requireContext(), "data saved", Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }

    private fun writeToLocal() {
        binding.apply {
            if (!TextUtils.isEmpty(editTextUserNameEditProfileDetails.text.toString())) {
                val name = editTextUserNameEditProfileDetails.text.toString()
                val mobile = editTextUserMobileNumberEditProfileDetails.text.toString()
                val address = editTextUserAddressEditProfileDetails.text.toString()
                val email = editTextUserEmailEditProfileDetails.text.toString()
                resumeViewModel.writeToLocal(name, mobile, address, email)
            } else {
                Toast.makeText(requireContext(), "empty data", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun defaultFormFill() {
        Log.d("lifeCycle", "formfill")
        viewLifecycleOwner.lifecycleScope.launch() {
            resumeViewModel.readToLocal.collect() { profile ->
                binding.textViewFillFormEditProfileDetails.text = "data saved"
                Log.d(
                    "saved_data",
                    "${profile.userName} ${profile.userMobile} ${profile.userEmail} ${profile.userAddress}"
                )
                binding.apply {
                    editTextUserNameEditProfileDetails.setText(profile.userName)
                    editTextUserMobileNumberEditProfileDetails.setText(profile.userMobile)
                    editTextUserAddressEditProfileDetails.setText(profile.userAddress)
                    editTextUserEmailEditProfileDetails.setText(profile.userEmail)
                }
            }
        }
    }
}
