package com.example.buildresume.ui.formeditorscreen

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.buildresume.R
import com.example.buildresume.UtilClass.gotoScreen
import com.example.buildresume.UtilClass.showToast
import com.example.buildresume.databinding.FragmentFormEditorScreenBinding
import com.example.buildresume.viewmodel.ResumeViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class FormEditorScreenFragment : Fragment() {

    private lateinit var binding: FragmentFormEditorScreenBinding

    private val resumeViewModel: ResumeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFormEditorScreenBinding.inflate(inflater, container, false)

        openProfileForm()
        openEducationForm()
        openSkillsForm()
        openProjectForm()
        openExperienceForm()
        generatePDF()

        return binding.root
    }

    private fun openProfileForm() {
        binding.buttonEditProfileFormEditorScreen.setOnClickListener {
            gotoScreen(
                view,
                FormEditorScreenFragmentDirections.actionFormEditorScreenFragmentToEditProfileDetailsFragment()
            )
        }
    }

    private fun openEducationForm() {
        binding.buttonEditEducationFormEditorScreen.setOnClickListener {
            gotoScreen(
                view,
                FormEditorScreenFragmentDirections.actionFormEditorScreenFragmentToEditEducationDetailsFragment()
            )
        }
    }

    private fun openSkillsForm() {
        binding.buttonEditSkillsFormEditorScreen.setOnClickListener {
            gotoScreen(
                view,
                FormEditorScreenFragmentDirections.actionFormEditorScreenFragmentToEditSkillsDetailsFragment()
            )
        }
    }

    private fun openProjectForm() {
        binding.buttonEditProjectFormEditorScreen.setOnClickListener {
            gotoScreen(
                view,
                FormEditorScreenFragmentDirections.actionFormEditorScreenFragmentToEditProjectDetailsFragment()
            )
        }
    }

    private fun openExperienceForm() {
        binding.buttonExperienceFormEditorScreen.setOnClickListener {
            gotoScreen(
                view,
                FormEditorScreenFragmentDirections.actionFormEditorScreenFragmentToEditExperienceDetailsFragment()
            )
        }
    }

    private fun generatePDF() {
        binding.buttonGeneratePdfFormEditorScreen.setOnClickListener {
            resumeViewModel.generatePdf(requireContext()) // library function
            //  resumeViewModel.localGeneratePDF(requireContext()) // local function for testing
            showPdf()
        }
    }

    private fun showPdf() {
        val file = File(requireContext().getExternalFilesDir("/"), "resumePDF.pdf")
        val target = Intent(Intent.ACTION_VIEW)
        target.data = Uri.fromFile(file)
        target.type = "application/pdf"
        // target.setDataAndType(Uri.fromFile(file), "application/pdf")
        target.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        val intent = Intent.createChooser(target, getString(R.string.select_app))

/*       val target = Intent(Intent.ACTION_VIEW)
        target.data = Uri.fromFile(file)
       target.type = "application/pdf"
        //target.setDataAndType(Uri.fromFile(file),"application/pdf")
       target.addCategory(Intent.CATEGORY_OPENABLE)
        startActivityForResult(Intent.createChooser(target,"Select PDF"),99)*/

        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            showToast(requireContext(), R.string.install_wps_app)
        }
    }

    private fun readStoredData() {
/*        resumeViewModel.allResumeList.observe(requireActivity() {
            form.userName = resume.userName
            form.userMobile = resume.userMobile
            form.userEmail = resume.userEmail
            form.userAddress = resume.userAddress
            form.schoolName = resume.schoolName
            form.schoolMarks = resume.schoolMarks
            form.collegeName = resume.collegeName
            form.collegeMarks = resume.collegeMarks
            form.diplomaCollegeName = resume.diplomaCollegeName
            form.diplomaCollegeMarks = resume.diplomaCollegeMarks
            form.degreeCollegeName = resume.degreeCollegeName
            form.degreeMarks = resume.degreeMarks
            form.programmingLanguage = resume.programmingLanguage
            form.softwareTools = resume.softwareTools
            form.certification = resume.certification
            form.otherSkills = resume.otherSkills
            form.projectTitle = resume.projectTitle
            form.projectDescription = resume.projectDescription
            form.companyName = resume.companyName
            form.companyExperienceYear = resume.companyExperienceYear
            form.totalExperience = resume.totalExperience
            if (form.isFormFilled()) {
                setIsResumeCreated(true)
            } else {
                setIsResumeCreated(false)
            }
        })
 */
    }
}
