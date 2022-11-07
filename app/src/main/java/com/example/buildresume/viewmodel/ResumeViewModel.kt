package com.example.buildresume.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.buildresume.data.Form
import com.example.buildresume.repository.EditDetailsRepository
import com.example.generatepdf.GeneratePdf
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResumeViewModel @Inject constructor(private val editDetailsRepository: EditDetailsRepository) : ViewModel() {

    val form = Form()

    fun generatePdf(context: Context) {
        form.run {
            GeneratePdf.build(
                context,
                userName, userMobile, userAddress, userEmail, schoolName, schoolMarks, collegeName, collegeMarks, diplomaCollegeName,
                diplomaCollegeMarks, degreeCollegeName, degreeMarks, programmingLanguage, softwareTools, certification, otherSkills,
            )
        }
    }

    private fun writeToLocal(
        userName: String, userMobile: String, userAddress: String, userEmail: String, schoolName: String, schoolMarks: String, collegeName: String, collegeMarks: String, diplomaCollegeName: String,
        diplomaCollegeMarks: String, degreeCollegeName: String, degreeMarks: String, programmingLanguage: String, softwareTools: String, certification: String, otherSkills: String
    ) = viewModelScope.launch {
        editDetailsRepository.writeToLocal(
            userName, userMobile, userAddress, userEmail, schoolName, schoolMarks, collegeName, collegeMarks, diplomaCollegeName,
            diplomaCollegeMarks, degreeCollegeName, degreeMarks, programmingLanguage, softwareTools, certification, otherSkills
        )
    }

    fun saveDataToLocal(){
        form.run {
            writeToLocal(
                userName, userMobile, userAddress, userEmail, schoolName, schoolMarks, collegeName, collegeMarks, diplomaCollegeName,
                diplomaCollegeMarks, degreeCollegeName, degreeMarks, programmingLanguage, softwareTools, certification, otherSkills
            )
        }
    }

    val readToLocal = editDetailsRepository.readToLocal
}