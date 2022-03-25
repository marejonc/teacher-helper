package com.example.teacherhelper.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(tableName = "student_subject_relation",
    foreignKeys = [
        ForeignKey(
            entity = Student::class,
            parentColumns = ["album_number"],
            childColumns = ["student_album_number"],
            onDelete = CASCADE),
        ForeignKey(
            entity = Subject::class,
            parentColumns = ["id"],
            childColumns = ["subject_id"],
            onDelete = CASCADE)
    ])
data class StudentSubjectRelation(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val Id: Int,
    @ColumnInfo(name = "subject_id")
    val SubjectID: Int,
    @ColumnInfo(name = "student_album_number")
    val StudentAlbumNumber: String?
)