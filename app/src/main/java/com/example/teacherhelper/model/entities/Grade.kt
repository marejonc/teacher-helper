package com.example.teacherhelper.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(tableName = "grades",
    foreignKeys = [
        ForeignKey(
            entity = StudentSubjectRelation::class,
            parentColumns = ["id"],
            childColumns = ["student_subject_relation_id"],
            onDelete = CASCADE)
    ])
data class Grade(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val Id: Int,
    @ColumnInfo(name = "student_subject_relation_id")
    val StudentSubjectRelationID: Int,
    @ColumnInfo(name = "grade_value")
    val GradeValue: Double
)