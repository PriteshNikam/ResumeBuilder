package com.example.buildresume.repository
import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.example.buildresume.ResumeDetails
import com.example.buildresume.data.Form
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

// repository
class EditDetailsRepository @Inject constructor
    (@ApplicationContext private val context: Context){
    private val Context.datastore : DataStore<ResumeDetails> by dataStore(
        fileName = "resumeData",
        serializer = DataStoreResume
    )

    suspend fun writeToLocal(
        userName:String, userMobile:String, userEmail:String, userAddress:String, schoolName:String, schoolMarks:String, collegeName:String,
        collegeMarks:String, diplomaCollegeName:String, diplomaCollegeMarks:String, degreeCollegeName:String, degreeMarks:String, programmingLanguage:String,
        softwareTools:String, certification:String, otherSkills:String, )
    = context.datastore.updateData{
        resume-> resume.toBuilder().setUserName(userName).setUserMobile(userMobile).setUserEmail(userEmail).setUserAddress(userAddress).setSchoolName(schoolName)
        .setSchoolMarks(schoolMarks).setCollegeName(collegeName).setCollegeMarks(collegeMarks).setDiplomaCollegeName(diplomaCollegeName).setDiplomaMarks(diplomaCollegeMarks)
        .setDegreeCollegeName(degreeCollegeName).setDegreeMarks(degreeMarks).setProgrammingLanguage(programmingLanguage).setSoftwareTools(softwareTools).setCertification(certification).setOtherSkills(otherSkills)
        .build()
    }

    val readToLocal: Flow<Form> = context.datastore.data
        .catch {
            if(this is Exception){
                Log.d("resume","${this.message}")
            }
        }.map {
            val form = Form(it.userName, it.userMobile, it.userAddress, it.userEmail, it.schoolName, it.schoolMarks,it.collegeName, it.collegeMarks,
                it.diplomaCollegeName, it.diplomaMarks, it.degreeCollegeName, it.degreeMarks, it.programmingLanguage, it.softwareTools, it.certification, it.otherSkills,
            )
            form
        }
}
