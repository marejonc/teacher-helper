package com.example.teacherhelper.model.repositories

import com.example.teacherhelper.model.database.StudentDAO
import com.example.teacherhelper.model.entities.Student

class StudentRepository private constructor(private val studentDAO: StudentDAO) {

    suspend fun addStudent(student: Student) = studentDAO.insertStudent(student)
    fun getStudents() = studentDAO.getAllStudents()
    suspend fun deleteAllStudents() = studentDAO.deleteAllStudents()

    companion object {
        @Volatile private var instance: StudentRepository? = null

        fun getInstance(studentDAO: StudentDAO) =
            instance ?: synchronized(this) {
                instance ?: StudentRepository(studentDAO).also { instance = it }
            }
    }
}