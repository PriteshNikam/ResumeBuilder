package com.example.buildresume.db

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.buildresume.data.Resume

class ResumeRepository(private var resumeDao:ResumeDao) {

    val allResumes : LiveData<List<Resume>> = resumeDao.getAllResume()

    suspend fun insertResume(resume: Resume){
        resumeDao.insert(resume)
    }

    suspend fun deleteResume(resume: Resume){
        resumeDao.delete(resume)
    }

    suspend fun updateResume(updateResume:Resume){
        resumeDao.update(updateResume)
    }
}