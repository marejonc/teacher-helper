package com.example.teacherhelper.viewmodel.vms

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.teacherhelper.model.database.TeacherHelperDatabase
import com.example.teacherhelper.model.entities.Student
import com.example.teacherhelper.model.repositories.StudentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StudentsViewModel(application: Application): AndroidViewModel(application) {

    private val repository = StudentRepository.getInstance(TeacherHelperDatabase.getInstance(application).studentDAO)
    val students: LiveData<List<Student>> = repository.getStudents()

    fun addStudent(student: Student) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addStudent(student)
        }
    }
}