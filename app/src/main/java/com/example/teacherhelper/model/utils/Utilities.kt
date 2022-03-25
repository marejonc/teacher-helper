package com.example.teacherhelper.model.utils

import androidx.lifecycle.LiveData
import com.example.teacherhelper.model.entities.Grade
import com.example.teacherhelper.model.entities.Student
import com.example.teacherhelper.model.entities.StudentSubjectRelation

object Utilities {
    fun albumNumberNotInList(albumNumber: String, students: LiveData<List<Student>>): Boolean {
        var result = true
        val n = students.value?.size!!
        for(i in 0 until n)
            if(students.value?.get(i)?.AlbumNumber == albumNumber)
                result = false
        return result
    }

    fun getStudentSubjectRelationId(albumNumber: String, subjectId: Int, relations: LiveData<List<StudentSubjectRelation>>): Int {
        var result = 0
        val n = relations.value?.size!!
        for(i in 0 until n)
            if(relations.value?.get(i)?.StudentAlbumNumber == albumNumber && relations.value?.get(i)?.SubjectID == subjectId)
                result = relations.value?.get(i)?.Id!!
        return result
    }

    fun calculateGradesMean(studentGrades: LiveData<List<Grade>>): Double {
        var result = 0.0
        var subjectsWithGrades = 0
        val allRelations = mutableListOf<Int>()
        studentGrades.value?.forEach {
            allRelations.add(it.StudentSubjectRelationID)
        }

        allRelations.distinct().forEach { relation ->
            var sum = 0.0
            var amount = 0
            studentGrades.value?.forEach {
                if(it.StudentSubjectRelationID == relation) {
                    amount++
                    sum += it.GradeValue
                }
            }
            if(amount != 0) {
                val mean = sum / amount
                when {
                    mean < 2.75 -> result += 2.0
                    mean < 3.25 -> result += 3.0
                    mean < 3.75 -> result += 3.5
                    mean < 4.25 -> result += 4.0
                    mean < 4.5 -> result += 4.5
                    else -> result += 5.0
                }
                subjectsWithGrades++
            }
        }

        return result / subjectsWithGrades
    }
}