package com.example.teacherhelper.model.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.teacherhelper.model.entities.Subject

@Dao
interface SubjectDAO {

    @Insert
    suspend fun insertSubject(subject: Subject)

    @Delete
    suspend fun deleteSubject(subject: Subject)

    @Query("select * from subjects")
    fun getAllSubjects(): LiveData<List<Subject>>

    @Query("select subjects.* from subjects, student_subject_relation where subjects.id = student_subject_relation.subject_id and student_subject_relation.student_album_number = :studentAlbumNumber")
    fun getAllStudentSubjects(studentAlbumNumber: String): LiveData<List<Subject>>

    @Query("delete from subjects")
    suspend fun deleteAllSubjects()
}