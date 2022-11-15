package com.example.buildresume.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.buildresume.data.Form

@Database(entities = [Form::class], version = 1, exportSchema = false)
abstract class ResumeDatabase:RoomDatabase() {
    abstract fun getResumeDao(): ResumeDao

    companion object {
        // Singleton prevents multiple instances of database opening at the same time.
        @Volatile
        private var INSTANCE: ResumeDatabase? = null

        fun getDatabase(context: Context): ResumeDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ResumeDatabase::class.java,
                    "resume_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

}