package com.example.buildresume.ui.homescreen

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.buildresume.UtilClass.onClick
import com.example.buildresume.UtilClass.showToast
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
        fragmentArgs =
            HomeScreenFragmentArgs.fromBundle(requireArguments())

        sharedPreferences = requireActivity().getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
        sharedPreferencesEditor = sharedPreferences.edit()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeScreenBinding.inflate(inflater, container, false)
        binding.textViewUserNameHomeScreen.text = fragmentArgs.user?.displayName

        prepareRecyclerView()

        return binding.root
    }

    private fun prepareRecyclerView(){
        if(sharedPreferences.getString(storedResumeKey,"").isNullOrEmpty()){
            binding.run {
                textViewEmptyTextHomeScreen.visibility = View.VISIBLE
                recyclerViewHomeScreen.visibility = View.GONE
            }
        }else {
            binding.run {
                recyclerViewHomeScreen.visibility = View.VISIBLE
                textViewEmptyTextHomeScreen.visibility = View.GONE
            }
        }

        binding.imageViewProfileHomeScreen.setOnClickListener{
            showToast(requireContext(),fragmentArgs.user?.displayName.toString().toInt())
        }

        binding.floatingButtonAddResumeHomeScreen.setOnClickListener{
            val uniqueID = UUID.randomUUID().toString()
            onClick(view,HomeScreenFragmentDirections.actionHomeScreenFragmentToFormEditorScreenFragment(uniqueID))
        }
    }

    companion object{
        private var storedResumeKey = "storedResume"
        private var PREF_FILE_NAME = "com.example.buildresume_preferences"
    }



}