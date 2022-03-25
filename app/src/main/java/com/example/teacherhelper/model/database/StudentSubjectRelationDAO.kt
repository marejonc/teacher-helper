package com.example.teacherhelper.model.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.teacherhelper.model.entities.Student
import com.example.teacherhelper.model.entities.StudentSubjectRelation

@Dao
interface StudentSubjectRelationDAO {

    @Insert
    suspend fun insertStudentSubjectRelation(studentSubjectRelation: StudentSubjectRelation)

    @Delete
    suspend fun deleteStudentSubjectRelation(studentSubjectRelation: StudentSubjectRelation)

    @Query("delete from student_subject_relation where student_album_number = :studentAlbumNumber and subject_id = :subjectId")
    suspend fun deleteStudentFromSubject(studentAlbumNumber: String, subjectId: Int)

    @Query("select * from student_subject_relation")
    fun getAllStudentSubjectRelations(): LiveData<List<StudentSubjectRelation>>

    @Query("select students.* from students, student_subject_relation where students.album_number = student_subject_relation.student_album_number and student_subject_relation.subject_id = :subjectId")
    fun getAllAttendingStudents(subjectId: Int): LiveData<List<Student>>

    @Query("delete from student_subject_relation")
    suspend fun deleteAllStudentSubjectRelations()
}