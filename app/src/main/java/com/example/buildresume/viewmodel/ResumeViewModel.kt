package com.example.buildresume.viewmodel

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.buildresume.data.Form
import com.example.buildresume.repository.EditDetailsRepository
import com.example.generatepdf.GeneratePdf
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class ResumeViewModel @Inject constructor(private val editDetailsRepository: EditDetailsRepository) :
    ViewModel() {

    val form = Form()

    fun generatePdf(context: Context) {
        form.run {
            GeneratePdf.build(
                context,
                userName,
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
                otherSkills,
                projectTitle,
                projectDescription,
                companyName,
                companyExperienceYear,
                totalExperience
            )
        }
    }

    private fun writeToLocal(
        userName: String,
        userMobile: String,
        userAddress: String,
        userEmail: String,
        schoolName: String,
        schoolMarks: String,
        collegeName: String,
        collegeMarks: String,
        diplomaCollegeName: String,
        diplomaCollegeMarks: String,
        degreeCollegeName: String,
        degreeMarks: String,
        programmingLanguage: String,
        softwareTools: String,
        certification: String,
        otherSkills: String,
        projectTitle: String,
        projectDescription: String,
        companyName:String,
        companyExperienceYear:String,
        totalExperience:String

    ) = viewModelScope.launch {
        editDetailsRepository.writeToLocal(
            userName,
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
            otherSkills,
            projectTitle,
            projectDescription,
            companyName,
            companyExperienceYear,
            totalExperience
        )
    }

    fun saveDataToLocal() {
        form.run {
            writeToLocal(
                userName,
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
                otherSkills,
                projectTitle,
                projectDescription,
                companyName,
                companyExperienceYear,
                totalExperience
            )
        }
    }

    val readToLocal = editDetailsRepository.readToLocal

    // to be delete after work complete
    fun localGeneratePDF(context: Context) {
        val myPdfDocument = PdfDocument()

        val bold = Typeface.DEFAULT_BOLD

        val paintHead1 = Paint()
        paintHead1.textSize = 25F
        paintHead1.color = Color.parseColor("#49599b")

        val paintLine = Paint()
        paintLine.strokeWidth = 2F
        paintLine.color = Color.parseColor("#FF03DAC5")

        val paintHead2 = Paint()
        paintHead2.textSize = 14F
        paintHead2.typeface = bold
        paintHead2.color = Color.parseColor("#FF03DAC5")

        val paintHead3 = Paint()
        paintHead3.textSize = 11F
        paintHead3.style = Paint.Style.FILL_AND_STROKE
        paintHead3.color = Color.BLACK

        val paintNormalText = Paint()
        paintNormalText.textSize = 10F

        val paintBoldText = Paint()
        paintBoldText.textSize = 10F
        paintBoldText.typeface = bold

        val pageInfo = PdfDocument.PageInfo.Builder(400, 700, 1).create()
        val page = myPdfDocument.startPage(pageInfo)
        val canvas = page.canvas

        var x = 20F
        var y = 20F

        // userName
        canvas.drawText(form.userName, 20F, 40F, paintHead1)

        //line
        canvas.drawLine(20F,50F,380F,50F,paintLine)

        canvas.drawText("Personal Details :", 30F, 70F, paintHead2)
        canvas.drawText("Mobile No. :", 35F, 90F, paintHead3) // mobile
        canvas.drawText(form.userMobile, 100F, 90F, paintNormalText)
        canvas.drawText("Address :", 35F, 110F, paintHead3) // address
        canvas.drawText(form.userAddress, 90F, 110F, paintNormalText)
        canvas.drawText("Email :", 35F, 130F, paintHead3) // email
        canvas.drawText(form.userEmail, 80F, 130F, paintNormalText)

        canvas.drawText("Education details :", 30F, 160F, paintHead2)
        canvas.drawText("10th School Name :", 35F, 180F, paintHead3) // 10th School name
        canvas.drawText(form.schoolName, 40F, 200F, paintNormalText)
        canvas.drawText(form.schoolMarks, 300F, 200F, paintBoldText)
        canvas.drawText("12th College Name :", 35F, 220F, paintHead3) //
        canvas.drawText(form.collegeName, 40F, 240F, paintNormalText)
        canvas.drawText(form.collegeMarks, 300F, 240F, paintBoldText)

        if(form.diplomaCollegeName.isNotEmpty()){
            canvas.drawText("Diploma College Name :", 35F, 260F, paintHead3) //
            canvas.drawText(form.diplomaCollegeName, 40F, 280F, paintNormalText)
            canvas.drawText(form.diplomaCollegeMarks, 300F, 280F, paintBoldText)

            canvas.drawText("Degree College Name :", 35F, 300F, paintHead3) //
            canvas.drawText(form.degreeCollegeName, 40F, 320F, paintNormalText)
            canvas.drawText(form.degreeMarks.toFloat().toString(), 320F, 320F, paintBoldText)

            canvas.drawText("Skills details: ", 30F, 350F, paintHead2)
            canvas.drawText("Programming languages :", 35F, 370F, paintHead3) //
            canvas.drawText(form.programmingLanguage, 170F, 370F, paintNormalText)
            canvas.drawText("Software tools :", 35F, 390F, paintHead3) //
            canvas.drawText(form.softwareTools, 120F, 390F, paintNormalText)
            canvas.drawText("Certifications :", 35F, 410F, paintHead3) //
            canvas.drawText(form.certification, 120F, 410F, paintNormalText)
            canvas.drawText("Other Skills :", 35F, 430F, paintHead3) //
            canvas.drawText(form.otherSkills, 110F, 430F, paintNormalText)
        }
        else{
            canvas.drawText("Degree College Name :", 35F, 260F, paintHead3) //
            canvas.drawText(form.degreeCollegeName, 40F, 280F, paintNormalText)
            canvas.drawText(form.degreeMarks.toFloat().toString(), 320F, 280F, paintBoldText)

            canvas.drawText("Skills details: ", 30F, 310F, paintHead2)
            canvas.drawText("Programming languages :", 35F, 330F, paintHead3) //
            canvas.drawText(form.programmingLanguage, 160F, 330F, paintNormalText)
            canvas.drawText("Software tools :", 35F, 370F, paintHead3) //
            canvas.drawText(form.softwareTools, 120F, 370F, paintNormalText)
            canvas.drawText("Certifications :", 35F, 410F, paintHead3) //
            canvas.drawText(form.certification, 120F, 410F, paintNormalText)
            canvas.drawText("Other Skills :", 35F, 450F, paintHead3) //
            canvas.drawText(form.otherSkills, 110F, 450F, paintNormalText)
        }

        myPdfDocument.finishPage(page)

        val file = File(context.getExternalFilesDir("/"), "resumePDF.pdf")

        try {
            myPdfDocument.writeTo(FileOutputStream(file))
            Toast.makeText(context, "PDF file generated successfully.", Toast.LENGTH_SHORT).show()
        } catch (e: IOException) {
            Toast.makeText(context, "PDF file not generated.", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
        myPdfDocument.close()
    }

    fun personalDetails(){

    }
    fun educationDetails(){

    }
    fun skillsDetails(){

    }
}
