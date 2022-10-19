package com.example.buildresume.ui.loginscreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.buildresume.R
import com.example.buildresume.databinding.FragmentLoginScreenBinding

class LoginScreen : Fragment() {

    private lateinit var binding:FragmentLoginScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginScreenBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.linearLayoutLoginScreen.setOnClickListener{
            Toast.makeText(requireContext(),"google login",Toast.LENGTH_SHORT).show()
        }

    }


}