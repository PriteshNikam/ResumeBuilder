package com.example.buildresume.data

data class Form(
    var userName:String = "",
    var userMobile:String = "",
    var userAddress:String = "",
    var userEmail:String = "",
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
    var totalExperience:String ="")

{
    fun isFormFilled(): Boolean {
        var result = false
        if (userName.isNotEmpty() ||
            userMobile.isNotEmpty() ||
            userAddress.isNotEmpty() ||
            userEmail.isNotEmpty() ||
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
