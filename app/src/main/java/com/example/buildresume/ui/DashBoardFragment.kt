package com.example.buildresume.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.buildresume.databinding.FragmentDashBoardBinding

class DashBoardFragment : Fragment() {

    private lateinit var fragmentArgs:DashBoardFragmentArgs
    private lateinit var binding:FragmentDashBoardBinding
    //private lateinit var firebaseAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentArgs = DashBoardFragmentArgs.fromBundle(requireArguments())

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View{
        binding = FragmentDashBoardBinding.inflate(inflater,container,false)
        binding.textViewDashBoard.text = fragmentArgs.user?.displayName
        return binding.root
    }

}