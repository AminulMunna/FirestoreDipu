package com.example.firestore

import com.google.firebase.Timestamp

data class User (
    val id:String?=null,
    val email:String?=null,
    val password:String?=null,
    val timeStamp:Timestamp?=null
)