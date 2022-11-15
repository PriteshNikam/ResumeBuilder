package com.example.buildresume.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = tableName)
data class Form(
    var userName:String = "",
    var userMobile:String = "",
    var userEmail:String = "",
    var userAddress:String = "",
    var schoolName:String = "",
    var schoolMarks:String = "",
    var collegeName:String = "",
    var collegeMarks:String = "",
    var diplomaCollegeName:String = "",
    var diplomaCollegeMarks:String = "",
    var degreeCollegeName:String  = "",
    var degreeMarks:String = "",
    var programmingLanguage:String = "",
    var softwareTools:String = "",
    var certification:String = "",
    var otherSkills:String= "",
    var projectTitle:String = "",
    var projectDescription:String = "",
    var companyName:String = "",
    var companyExperienceYear:String = "",
    var totalExperience:String ="",
    var resumeTime:String = ""
    ):Parcelable {
    @PrimaryKey(autoGenerate = true)
    var resumeId = 0

    fun isFormFilled(): Boolean {
        var result = false
        if (userName.isNotEmpty() ||
            userMobile.isNotEmpty() ||
            userEmail.isNotEmpty() ||
            userAddress.isNotEmpty() ||
            schoolName.isNotEmpty() ||
            schoolMarks.isNotEmpty() ||
            collegeName.isNotEmpty() ||
            collegeMarks.isNotEmpty() ||
            diplomaCollegeName.isNotEmpty() ||
            diplomaCollegeMarks.isNotEmpty() ||
            degreeCollegeName.isNotEmpty() ||
            degreeMarks.isNotEmpty() ||
            programmingLanguage.isNotEmpty() ||
            softwareTools.isNotEmpty() ||
            certification.isNotEmpty() ||
            otherSkills.isNotEmpty() ||
            projectTitle.isNotEmpty() ||
            projectDescription.isNotEmpty() ||
            companyName.isNotEmpty() ||
            companyExperienceYear.isNotEmpty() ||
            totalExperience.isNotEmpty()) {
            result = true
        }
        return result
    }

}

const val tableName = "resumes_tables"
