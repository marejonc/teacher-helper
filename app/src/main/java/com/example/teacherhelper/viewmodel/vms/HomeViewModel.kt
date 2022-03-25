package com.example.teacherhelper.viewmodel.vms

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.teacherhelper.model.database.TeacherHelperDatabase
import com.example.teacherhelper.model.repositories.GradeRepository
import com.example.teacherhelper.model.repositories.StudentRepository
import com.example.teacherhelper.model.repositories.StudentSubjectRelationRepository
import com.example.teacherhelper.model.repositories.SubjectRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(application: Application): AndroidViewModel(application) {

    private val repositorySubjects = SubjectRepository.getInstance(TeacherHelperDatabase.getInstance(application).subjectDAO)
    private val repositoryStudents = StudentRepository.getInstance(TeacherHelperDatabase.getInstance(application).studentDAO)
    private val repositoryGrades = GradeRepository.getInstance(TeacherHelperDatabase.getInstance(application).gradeDAO)
    private val repositoryStudentSubjectRelations = StudentSubjectRelationRepository
        .getInstance(TeacherHelperDatabase.getInstance(application).studentSubjectRelationDAO)

    fun deleteAllData() {
        viewModelScope.launch(Dispatchers.IO) {
            repositoryStudents.deleteAllStudents()
            repositorySubjects.deleteAllSubjects()
            repositoryGrades.deleteAllGrades()
            repositoryStudentSubjectRelations.deleteAllStudentSubjectRelations()
        }
    }
}