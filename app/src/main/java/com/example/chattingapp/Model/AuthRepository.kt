package com.example.chattingapp.Model

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class AuthRepository(val Store:FirebaseFirestore,val Auth:FirebaseAuth){
    suspend fun SignUp(Email:String,pass:String,FN:String,LN:String):Result<Boolean> = try{
        Auth.createUserWithEmailAndPassword(Email,pass).await()
        AddToFireStore(data = User(Email,FN,LN))
        Result.Success(true)
    }catch (e:Exception){
        Result.Error(e)
    }
    suspend fun AddToFireStore(data:User){
        Store.collection("Users").document(data.Email).set(data).await()
    }
    suspend fun login(email: String, password: String): Result<Boolean> =
        try {
            Auth.signInWithEmailAndPassword(email, password).await()
            Result.Success(true)
        } catch (e: Exception) {
            Result.Error(e)
        }
    suspend fun getCurrentUser(): Result<User> = try {
        val uid = Auth.currentUser?.email
        if (uid != null) {
            val userDocument = Store.collection("users").document(uid).get().await()
            val user = userDocument.toObject(User::class.java)
            if (user != null) {
                Log.d("user2","$uid")
                Result.Success(user)
            } else {
                Result.Error(Exception("User data not found"))
            }
        } else {
            Result.Error(Exception("User not authenticated"))
        }
    } catch (e: Exception) {
        Result.Error(e)
    }
}