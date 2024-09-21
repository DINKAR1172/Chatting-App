package com.example.chattingapp

 sealed class Screens(val route:String) {
     object SignUpScreen:Screens("SignUpScreen")
     object SigninScreen:Screens("SigninScreen")
     object Rooms:Screens("Rooms")
     object ChattingScreen:Screens("ChattingScreen")
}