package com.example.chattingapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.chattingapp.Screen.ChattingScreen
import com.example.chattingapp.Screen.Rooms
import com.example.chattingapp.Screen.SignUp
import com.example.chattingapp.Screen.Signin
import com.example.chattingapp.ViewModel.AuthViewModel
import com.example.chattingapp.ViewModel.MessageViewModel
import com.example.chattingapp.ViewModel.RoomViewModel
import com.example.chattingapp.ui.theme.ChattingAppTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChattingAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
Navigation()
                }
            }
        }
    }
}
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigation(){
    val context= LocalContext.current
    val AuthViewModel:AuthViewModel= viewModel()
    val RoomViewModel:RoomViewModel= viewModel()
    val MessageViewModel:MessageViewModel= viewModel()
    val navHostController= rememberNavController()
    NavHost(navController =navHostController , startDestination =Screens.SignUpScreen.route ){
        composable(Screens.SignUpScreen.route){
            SignUp(navHostController,AuthViewModel)
        }
        composable(Screens.SigninScreen.route){
Signin(navigation = navHostController,AuthViewModel,context)
        }
        composable(Screens.Rooms.route){
            Rooms(RoomViewModel,navHostController,MessageViewModel)
        }
        composable(Screens.ChattingScreen.route){
            ChattingScreen(RoomViewModel,MessageViewModel)
        }

    }
}

