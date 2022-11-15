package com.example.buildresume.db

import androidx.lifecycle.LiveData
import com.example.buildresume.data.Form

class ResumeRepository(private var resumeDao:ResumeDao) {

    val allResumes : LiveData<List<Form>> = resumeDao.getAllResume()

    suspend fun insertNote(note: Form){
        resumeDao.insert(note)
    }

    suspend fun deleteNote(note: Form){
        resumeDao.delete(note)
    }

    suspend fun updateNote(updateNote:Form){
        resumeDao.resumeUpdate(updateNote)
    }
}