package com.example.teacherhelper.model.repositories

import com.example.teacherhelper.model.database.StudentSubjectRelationDAO
import com.example.teacherhelper.model.entities.StudentSubjectRelation

class StudentSubjectRelationRepository private constructor(private val studentSubjectRelationDAO: StudentSubjectRelationDAO) {

    suspend fun addStudentSubjectRelation(studentSubjectRelation: StudentSubjectRelation) =
        studentSubjectRelationDAO.insertStudentSubjectRelation(studentSubjectRelation)
    fun getAllAttendingStudents(subjectId: Int) = studentSubjectRelationDAO.getAllAttendingStudents(subjectId)
    fun getAllStudentSubjectRelations() = studentSubjectRelationDAO.getAllStudentSubjectRelations()
    suspend fun deleteStudentFromSubject(studentAlbumNumber: String, subjectId: Int) =
        studentSubjectRelationDAO.deleteStudentFromSubject(studentAlbumNumber, subjectId)
    suspend fun deleteAllStudentSubjectRelations() = studentSubjectRelationDAO.deleteAllStudentSubjectRelations()

    companion object {
        @Volatile private var instance: StudentSubjectRelationRepository? = null

        fun getInstance(studentSubjectRelationDAO: StudentSubjectRelationDAO) =
            instance ?: synchronized(this) {
                instance ?: StudentSubjectRelationRepository(studentSubjectRelationDAO).also { instance = it }
            }
    }
}