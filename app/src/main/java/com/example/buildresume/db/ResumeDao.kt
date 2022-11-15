package com.example.buildresume.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.buildresume.data.Form

@Dao
interface ResumeDao {
    @Query( "Select * from resumes_tables order by resumeTime DESC")
    fun getAllResume(): LiveData<List<Form>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(resume: Form)

    @Delete
    suspend fun delete(resume:Form)

    @Update
    suspend fun resumeUpdate(resume: Form)
}