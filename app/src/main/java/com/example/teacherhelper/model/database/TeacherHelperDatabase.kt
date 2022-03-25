package com.example.teacherhelper.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.teacherhelper.model.entities.Grade
import com.example.teacherhelper.model.entities.Student
import com.example.teacherhelper.model.entities.StudentSubjectRelation
import com.example.teacherhelper.model.entities.Subject

@Database(entities = [Student::class, Subject::class, Grade::class, StudentSubjectRelation::class],
    version = 1, exportSchema = false)
abstract class TeacherHelperDatabase: RoomDatabase() {

    //Data Access Objects
    abstract val studentDAO: StudentDAO
    abstract val subjectDAO: SubjectDAO
    abstract val gradeDAO: GradeDAO
    abstract val studentSubjectRelationDAO: StudentSubjectRelationDAO

    companion object {
        @Volatile
        private var INSTANCE: TeacherHelperDatabase? = null

        fun getInstance(context: Context): TeacherHelperDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if(instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        TeacherHelperDatabase::class.java,
                        "teacher_helper_database"
                    ).fallbackToDestructiveMigration().build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}