package com.example.teacherhelper.viewmodel.vms

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.teacherhelper.model.database.TeacherHelperDatabase
import com.example.teacherhelper.model.entities.Student
import com.example.teacherhelper.model.entities.StudentSubjectRelation
import com.example.teacherhelper.model.repositories.StudentRepository
import com.example.teacherhelper.model.repositories.StudentSubjectRelationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditSubjectViewModel(application: Application): AndroidViewModel(application) {

    private val repository = StudentSubjectRelationRepository.getInstance(TeacherHelperDatabase.getInstance(application).studentSubjectRelationDAO)
    private val studentsRepository = StudentRepository.getInstance(TeacherHelperDatabase.getInstance(application).studentDAO)
    private val _subjectId = MutableLiveData<Int>()
    var students: LiveData<List<Student>> = Transformations.switchMap(_subjectId) { studentsRepository.getStudents() }
    var attendingStudents: LiveData<List<Student>> = Transformations.switchMap(_subjectId) { id -> repository.getAllAttendingStudents(id) }

    fun setSubjectId(id: Int) { _subjectId.value = id }

    fun addStudentSubjectRelation(studentSubjectRelation: StudentSubjectRelation) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addStudentSubjectRelation(studentSubjectRelation)
        }
    }

    fun deleteStudentFromSubject(studentAlbumNumber: String, subjectId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteStudentFromSubject(studentAlbumNumber, subjectId)
        }
    }
}