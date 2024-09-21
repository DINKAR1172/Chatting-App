package com.example.chattingapp.Model

data class User(val Email:String,val FirstNAme:String,val LastName:String)
data class Room(
    val id: String = "",
    val name: String = ""
)
data class Message(
    val senderFirstName: String = "",
    val senderId:String = "",
    val text: String = "",
    val timestamp: Long = System.currentTimeMillis(),
    val isSentByCurrentUser: Boolean = false

)
