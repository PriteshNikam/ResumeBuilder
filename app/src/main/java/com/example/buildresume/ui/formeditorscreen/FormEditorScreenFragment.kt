package com.example.buildresume.ui.formeditorscreen

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.buildresume.databinding.FragmentFormEditorScreenBinding

class FormEditorScreenFragment : Fragment() {

    private lateinit var binding: FragmentFormEditorScreenBinding
    private lateinit var fragmentArgs: FormEditorScreenFragmentArgs
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var sharedPreferencesEditor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fragmentArgs = FormEditorScreenFragmentArgs.fromBundle(requireArguments())
        sharedPreferences = requireActivity().getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
        sharedPreferencesEditor = sharedPreferences.edit()
        sharedPreferencesEditor.putString(storedResumeKey,fragmentArgs.userID.toString())
        sharedPreferencesEditor.commit()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFormEditorScreenBinding.inflate(inflater,container,false)

        Log.d("id",fragmentArgs.userID.toString()) // store uid to sharedPreference

        binding.textViewFormEditorScreen.text = fragmentArgs.userID.toString()

        return  binding.root
    }

    companion object {
        private var storedResumeKey = "storedResume"
        private var TAG = "editorScreen"
        private var PREF_FILE_NAME = "com.example.buildresume_preferences"
        private var TOKEN_KEY = "token_key"
    }
}