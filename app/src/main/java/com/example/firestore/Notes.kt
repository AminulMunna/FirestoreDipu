package com.example.firestore

import com.google.firebase.Timestamp

data class Notes (
    var id : String?=null,
    val title:String?=null,
    val description : String?=null,
    val timeStamp : Timestamp?=null
)