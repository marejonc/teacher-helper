package com.example.teacherhelper.model.repositories

import com.example.teacherhelper.model.database.GradeDAO
import com.example.teacherhelper.model.entities.Grade

class GradeRepository private constructor(private val gradeDAO: GradeDAO) {

    suspend fun addGrade(grade: Grade) = gradeDAO.insertGrade(grade)
    fun getAllStudentGrades(studentAlbumNumber: String) = gradeDAO.getAllStudentGrades(studentAlbumNumber)
    suspend fun deleteAllGrades() = gradeDAO.deleteAllGrades()

    companion object {
        @Volatile private var instance: GradeRepository? = null

        fun getInstance(gradeDAO: GradeDAO) =
            instance ?: synchronized(this) {
                instance ?: GradeRepository(gradeDAO).also { instance = it }
            }
    }
}