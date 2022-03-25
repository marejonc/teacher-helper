package com.example.teacherhelper.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "subjects")
data class Subject(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val Id: Int = 0,
    @ColumnInfo(name = "name")
    val Name: String,
    @ColumnInfo(name = "group")
    val Group: String,
    @ColumnInfo(name = "week_day")
    val WeekDay: String,
    @ColumnInfo(name = "hours")
    val Hours: String
)