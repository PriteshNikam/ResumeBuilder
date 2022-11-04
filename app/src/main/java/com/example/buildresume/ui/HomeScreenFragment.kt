package com.example.buildresume.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.buildresume.databinding.FragmentHomeScreenBinding
import java.util.*

class HomeScreenFragment : Fragment() {

    private lateinit var fragmentArgs: HomeScreenFragmentArgs
    private lateinit var binding: FragmentHomeScreenBinding
    //private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var sharedPreferencesEditor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentArgs = HomeScreenFragmentArgs.fromBundle(requireArguments())

        sharedPreferences = requireActivity().getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
        sharedPreferencesEditor = sharedPreferences.edit()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeScreenBinding.inflate(inflater, container, false)
        binding.textViewUserNameHomeScreen.text = fragmentArgs.user?.displayName

        storedResume()

        return binding.root
    }

    private fun storedResume(){
        if(sharedPreferences.getString(storedResumeKey,"").isNullOrEmpty()){
            binding.textViewEmptyTextHomeScreen.visibility = View.VISIBLE
            binding.recyclerViewHomeScreen.visibility = View.GONE
        }else {
            binding.recyclerViewHomeScreen.visibility = View.VISIBLE
            binding.textViewEmptyTextHomeScreen.visibility = View.GONE
        }

        binding.imageViewProfileHomeScreen.setOnClickListener{
            createToast(fragmentArgs.user?.displayName.toString())
        }

        binding.floatingButtonAddResumeHomeScreen.setOnClickListener{
            val uniqueID = UUID.randomUUID().toString()
            view?.findNavController()?.navigate(HomeScreenFragmentDirections.actionHomeScreenFragmentToFormEditorScreenFragment(uniqueID))
        }
    }

    companion object{
        private var storedResumeKey = "storedResume"
        private var PREF_FILE_NAME = "com.example.buildresume_preferences"
    }

    private fun createToast(message: String = "") {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

}