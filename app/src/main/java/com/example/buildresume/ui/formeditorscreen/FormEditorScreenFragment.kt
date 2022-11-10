package com.example.buildresume.ui.formeditorscreen

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.buildresume.UtilClass.onClick
import com.example.buildresume.UtilClass.showLog
import com.example.buildresume.databinding.FragmentFormEditorScreenBinding
import com.example.buildresume.viewmodel.ResumeViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.File


@AndroidEntryPoint
class FormEditorScreenFragment : Fragment() {

    private lateinit var binding: FragmentFormEditorScreenBinding

    private val resumeViewModel: ResumeViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
            PackageManager.PERMISSION_GRANTED
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFormEditorScreenBinding.inflate(inflater, container, false)


        binding.apply {
            buttonEditProfileFormEditorScreen.setOnClickListener {
                onClick(view,FormEditorScreenFragmentDirections.actionFormEditorScreenFragmentToEditProfileDetailsFragment())
            }

            buttonEditEducationFormEditorScreen.setOnClickListener {
                onClick(view,FormEditorScreenFragmentDirections.actionFormEditorScreenFragmentToEditEducationDetailsFragment())
            }

            buttonEditSkillsFormEditorScreen.setOnClickListener {
                onClick(view,FormEditorScreenFragmentDirections.actionFormEditorScreenFragmentToEditSkillsDetailsFragment())
            }

            buttonEditProjectFormEditorScreen.setOnClickListener {
                onClick(view,FormEditorScreenFragmentDirections.actionFormEditorScreenFragmentToEditProjectDetailsFragment())
            }

            buttonExperienceFormEditorScreen.setOnClickListener {
                onClick(view,FormEditorScreenFragmentDirections.actionFormEditorScreenFragmentToEditExperienceDetailsFragment())
            }
        }

        //binding.textViewFormEditorScreen.text = fragmentArgs.userID.toString()


        binding.buttonGeneratePdfFormEditorScreen.setOnClickListener {
            resumeViewModel.generatePdf(requireContext()) // library function
          // resumeViewModel.localGeneratePDF(requireContext()) // local function for testing
            showPdf()
        }

        return binding.root
    }

    private fun showPdf() {

        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
            PackageManager.PERMISSION_GRANTED
        )

        val file = File("${Environment.getExternalStorageDirectory().absolutePath}/resumePDF.pdf")

        val target = Intent(Intent.ACTION_VIEW)
       target.data = Uri.fromFile(file)
       target.type = "application/pdf"
        //target.setDataAndType(Uri.fromFile(file), "application/pdf")
        target.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP

        val intent = Intent.createChooser(target, "Open File")
        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            // Instruct the user to install a PDF reader here, or something
        }
    }

    companion object {
        private var storedResumeKey = "storedResume"
        private var PREF_FILE_NAME = "com.example.buildresume_preferences"
    }
}