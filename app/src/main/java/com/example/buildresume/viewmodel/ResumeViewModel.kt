package com.example.buildresume.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.buildresume.data.Form
import com.example.buildresume.repository.ProfileEditDetailsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResumeViewModel @Inject constructor(private val profileEditDetailsRepository: ProfileEditDetailsRepository):ViewModel() {

    private var userName = MutableLiveData<String>()
    private var userMobile = MutableLiveData<String>()
    private var userAddress = MutableLiveData<String>()
    private var userEmail = MutableLiveData<String>()
    private var schoolName = MutableLiveData<String>()
    private var schoolMarks = MutableLiveData<String>()
    private var collegeName = MutableLiveData<String>()
    private var collegeMarks = MutableLiveData<String>()
    private var diplomaCollegeName = MutableLiveData<String>()
    private var diplomaCollegeMarks = MutableLiveData<String>()
    private var degreeCollegeName = MutableLiveData<String>()
    private var degreeMarks = MutableLiveData<String>()
    private var programmingLanguage = MutableLiveData<String>()
    private var softwareTools = MutableLiveData<String>()
    private var certification = MutableLiveData<String>()
    private var otherSkills = MutableLiveData<String>()


    var readUserName : LiveData<String> = userName
    var readUserMobile: LiveData<String>  = userMobile
    var readUserAddress:LiveData<String>  = userAddress
    var readUserEmail: LiveData<String>  = userEmail
    var readSchoolName: LiveData<String>  =schoolName
    var readSchoolMarks: LiveData<String>  = schoolMarks
    var readCollegeName: LiveData<String>  = collegeName
    var readCollegeMarks : LiveData<String>  = collegeMarks
    var readDiplomaCollegeName: LiveData<String>  = diplomaCollegeName
    var readDiplomaCollegeMarks: LiveData<String>  =  diplomaCollegeMarks
    var readDegreeCollegeName: LiveData<String>  = degreeCollegeName
    var readDegreeMarks: LiveData<String>  = degreeMarks
    var readProgrammingLanguage: LiveData<String>  = programmingLanguage
    var readSoftwareTools: LiveData<String>  = softwareTools
    var readCertification: LiveData<String>  =certification
    var readOtherSkills : LiveData<String>  = otherSkills

    fun setUserName(value: String){userName.value = value}
    fun setUserMobile(value: String){userMobile.value = value}
    fun setUserAddress(value: String){userAddress.value = value}
    fun setUserEmail(value: String){userEmail.value = value}
    fun setSchoolName(value: String){schoolName.value = value}
    fun setSchoolMarks(value: String){schoolMarks.value = value}
    fun setCollegeName(value: String){schoolName.value = value}
    fun setCollegeMarks(value: String){collegeName.value = value}
    fun setDiplomaCollegeName(value: String){diplomaCollegeName.value = value}
    fun setDiplomaMarks(value: String){diplomaCollegeMarks.value = value}
    fun setDegreeCollegeName(value: String){degreeCollegeName.value = value}
    fun setDegreeMarks(value: String){degreeMarks.value = value}
    fun setProgrammingLanguage(value: String){programmingLanguage.value = value}
    fun setSoftwareTools(value: String){softwareTools.value = value}
    fun setCertification(value: String){certification.value = value}
    fun setOtherSkills(value: String){otherSkills.value = value}


    fun writeToLocal(userName:String,
                     userMobile:String,
                     userAddress:String,
                     userEmail:String,
                      schoolName:String,
                      schoolMarks:String,
                      collegeName:String,
                      collegeMarks:String,
                      diplomaCollegeName:String,
                      diplomaCollegeMarks:String,
                      degreeCollegeName:String,
                      degreeMarks:String,
                      programmingLanguage:String,
                      softwareTools:String,
                      certification:String,
                      otherSkills:String
                     ) = viewModelScope.launch {
        profileEditDetailsRepository.writeToLocal(userName,
            userMobile,
            userAddress,
            userEmail,
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
            otherSkills
        )
    }

    val readToLocal =  profileEditDetailsRepository.readToLocal
}