package com.example.teacherhelper.model.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.teacherhelper.model.entities.Student

@Dao
interface StudentDAO {

    @Insert
    suspend fun insertStudent(student: Student)

    @Delete
    suspend fun deleteStudent(student: Student)

    @Query("select * from students")
    fun getAllStudents(): LiveData<List<Student>>

    @Query("delete from students")
    suspend fun deleteAllStudents()
}