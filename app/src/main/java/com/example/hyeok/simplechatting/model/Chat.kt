package com.example.hyeok.simplechatting.model

import com.google.firebase.database.ServerValue

data class Chat(
        val userName : String,
        val message : String,
        val timestamp : Any = ServerValue.TIMESTAMP
)