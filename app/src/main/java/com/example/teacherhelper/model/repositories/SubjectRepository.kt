package com.example.teacherhelper.model.repositories

import com.example.teacherhelper.model.database.SubjectDAO
import com.example.teacherhelper.model.entities.Subject

class SubjectRepository private constructor(private val subjectDAO: SubjectDAO) {

    suspend fun addSubject(subject: Subject) = subjectDAO.insertSubject(subject)
    fun getSubjects() = subjectDAO.getAllSubjects()
    fun getStudentSubjects(studentAlbumNumber: String) = subjectDAO.getAllStudentSubjects(studentAlbumNumber)
    suspend fun deleteAllSubjects() = subjectDAO.deleteAllSubjects()

    companion object {
        @Volatile private var instance: SubjectRepository? = null

        fun getInstance(subjectDAO: SubjectDAO) =
            instance ?: synchronized(this) {
                instance ?: SubjectRepository(subjectDAO).also { instance = it }
            }
    }
}