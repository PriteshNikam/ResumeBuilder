package com.example.buildresume.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.buildresume.data.DataModel
import com.example.buildresume.data.Resume
import com.example.buildresume.data.WelcomeCard
import com.example.buildresume.db.ResumeDatabase
import com.example.buildresume.db.ResumeRepository
import com.example.generatepdf.GeneratePdf
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ResumeViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = ResumeDatabase.getDatabase(application).getResumeDao()
    private val resumeRepository = ResumeRepository(dao)

    var signInUser:String = ""

    var resume = Resume()

    var allResumeList: LiveData<List<Resume>> = resumeRepository.allResumes

    fun insertResumeInLocal() {
        viewModelScope.launch(Dispatchers.IO) {
            resumeRepository.insertResume(resume)
        }
    }

    fun deleteResumeInLocal(resume: Resume) {
        viewModelScope.launch(Dispatchers.IO) {
            resumeRepository.deleteResume(resume)
        }
    }

    fun updateResumeInLocal(updatedResume: Resume) {
        viewModelScope.launch(Dispatchers.IO) {
            resumeRepository.updateResume(updatedResume)
        }
    }

    fun generatePdf(context: Context) {
        resume.run {
            GeneratePdf.build(
                context,
                userName,
                userMobile,
                userEmail,
                userAddress,
                schoolName,
                schoolMarks,
                collegeName,
                collegeMarks,
                diplomaCollegeName,
                diplomaCollegeMarks,
                degreeCollegeName,
                degreeMarks,
                programmingLanguage,
                softwareTools,
                certification,
                otherSkills,
                projectTitle,
                projectDescription,
                companyName,
                companyExperienceYear,
                totalExperience,
                resumeTime
            )
        }
    }

    fun setRecyclerViewResume(resume: Resume) {
        this.resume = resume
    }

    fun setDefaultList(resumeList:List<DataModel>):MutableList<DataModel>{
        val defaultList = mutableListOf<DataModel>()
        defaultList.add(0, WelcomeCard(""))
        defaultList.addAll(resumeList)
       // defaultList.add(3,WelcomeCard(""))
       // defaultList.add(6,WelcomeCard(""))
        return defaultList
    }

}
