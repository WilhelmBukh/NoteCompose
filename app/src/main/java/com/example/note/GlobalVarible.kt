package com.example.note

import com.example.note.db.Daodb

lateinit var globalDao:Daodb

enum class doRoom{
    insert,update,delete
}
var isFirstLaunch:Boolean = true

var selectedScreen:String = ""