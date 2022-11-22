package com.example.buildresume.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.buildresume.data.Resume

@Dao
interface ResumeDao {
    @Query( "Select * from resumes_tables order by resumeTime DESC")
    fun getAllResume(): LiveData<List<Resume>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(resume: Resume)

    @Delete
    suspend fun delete(resume:Resume)

    @Update
    suspend fun update(resume: Resume)
}