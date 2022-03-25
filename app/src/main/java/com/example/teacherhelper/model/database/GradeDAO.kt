package com.example.teacherhelper.model.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.teacherhelper.model.entities.Grade
import com.example.teacherhelper.model.entities.Student

@Dao
interface GradeDAO {

    @Insert
    suspend fun insertGrade(grade: Grade)

    @Delete
    suspend fun deleteGrade(grade: Grade)

    @Query("select grades.* from grades, student_subject_relation where grades.student_subject_relation_id = student_subject_relation.id and student_subject_relation.student_album_number = :studentAlbumNumber")
    fun getAllStudentGrades(studentAlbumNumber: String): LiveData<List<Grade>>

    @Query("delete from grades")
    suspend fun deleteAllGrades()
}