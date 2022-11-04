package com.example.buildresume.ui.formeditorscreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.buildresume.R
import com.example.buildresume.databinding.FragmentEditEducationDetailsBinding


class EditEducationDetailsFragment : Fragment() {

    private lateinit var binding :FragmentEditEducationDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentEditEducationDetailsBinding.inflate(inflater,container,false)
        return binding.root
    }
}