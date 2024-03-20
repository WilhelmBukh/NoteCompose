package com.example.note.db


import androidx.compose.material.icons.Icons
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note")
class Modeldb(
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0,
    @ColumnInfo
    var title:String = "",
    @ColumnInfo
    var text:String = "",
    @ColumnInfo
    var selected:Boolean = false,
)