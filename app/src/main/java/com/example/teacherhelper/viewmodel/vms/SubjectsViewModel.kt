package com.example.teacherhelper.viewmodel.vms

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.teacherhelper.model.database.TeacherHelperDatabase
import com.example.teacherhelper.model.entities.Subject
import com.example.teacherhelper.model.repositories.SubjectRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SubjectsViewModel(application: Application): AndroidViewModel(application) {

    private val repository = SubjectRepository.getInstance(TeacherHelperDatabase.getInstance(application).subjectDAO)
    val subjects: LiveData<List<Subject>> = repository.getSubjects()

    fun addSubject(subject: Subject) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addSubject(subject)
        }
    }
}