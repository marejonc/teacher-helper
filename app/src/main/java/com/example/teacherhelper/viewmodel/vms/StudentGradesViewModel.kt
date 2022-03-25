package com.example.teacherhelper.viewmodel.vms

import android.app.Application
import androidx.lifecycle.*
import com.example.teacherhelper.model.database.TeacherHelperDatabase
import com.example.teacherhelper.model.entities.Grade
import com.example.teacherhelper.model.entities.StudentSubjectRelation
import com.example.teacherhelper.model.entities.Subject
import com.example.teacherhelper.model.repositories.GradeRepository
import com.example.teacherhelper.model.repositories.StudentSubjectRelationRepository
import com.example.teacherhelper.model.repositories.SubjectRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StudentGradesViewModel(application: Application): AndroidViewModel(application) {

    private val repository = GradeRepository.getInstance(TeacherHelperDatabase.getInstance(application).gradeDAO)
    private val subjectsRepository = SubjectRepository.getInstance(TeacherHelperDatabase.getInstance(application).subjectDAO)
    private val studentSubjectRelationRepository = StudentSubjectRelationRepository
        .getInstance(TeacherHelperDatabase.getInstance(application).studentSubjectRelationDAO)
    private val _studentAlbumNumber = MutableLiveData<String>()
    var studentGrades: LiveData<List<Grade>> = Transformations.switchMap(_studentAlbumNumber) { number -> repository.getAllStudentGrades(number) }
    var subjects: LiveData<List<Subject>> = Transformations.switchMap(_studentAlbumNumber) { number -> subjectsRepository.getStudentSubjects(number) }
    var studentSubjectRelations: LiveData<List<StudentSubjectRelation>> =
        Transformations.switchMap(_studentAlbumNumber) { studentSubjectRelationRepository.getAllStudentSubjectRelations() }

    fun setAlbumNumber(albumNumber: String) { _studentAlbumNumber.value = albumNumber }

    fun addGrade(grade: Grade) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addGrade(grade)
        }
    }
}