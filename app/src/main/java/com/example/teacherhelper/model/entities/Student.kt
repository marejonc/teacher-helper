package com.example.teacherhelper.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "students")
data class Student(
    @PrimaryKey
    @ColumnInfo(name = "album_number")
    val AlbumNumber: String,
    @ColumnInfo(name = "first_name")
    val FirstName: String,
    @ColumnInfo(name = "last_name")
    val LastName: String
)