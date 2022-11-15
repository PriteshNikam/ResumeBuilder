package com.example.buildresume.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.buildresume.data.Form
import com.example.buildresume.db.ResumeDatabase
import com.example.buildresume.db.ResumeRepository
import com.example.generatepdf.GeneratePdf
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ResumeViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = ResumeDatabase.getDatabase(application).getResumeDao()
    private val resumeRepository = ResumeRepository(dao)

    private var isResumeCreated =  MutableLiveData<Boolean>().apply { postValue(false) }
    var resumeCreated: LiveData<Boolean> = isResumeCreated
    var form = Form()

    private fun setIsResumeCreated(status:Boolean){
        isResumeCreated.value = status
    }

    var allResumeList: LiveData<List<Form>> = resumeRepository.allResumes

    fun saveDataToLocal() {
        viewModelScope.launch(Dispatchers.IO) {
            resumeRepository.insertNote(form)
        }
    }

    fun delete(form:Form) {
        viewModelScope.launch(Dispatchers.IO) {
            resumeRepository.deleteNote(form)
        }
    }

    fun update(form:Form) {
        viewModelScope.launch(Dispatchers.IO) {
            resumeRepository.updateNote(form)
        }
    }

    fun generatePdf(context: Context) {
        form.run {
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
                totalExperience
            )
        }
    }

    fun openEditedResume(id:Int){
        Log.d("resumeList","${allResumeList.value?.forEach { if(it.resumeId.equals(id)) println(it)}}")
    }

    fun setSpecificResumeFormDetails(form:Form){
        this.form = form
    }

}
