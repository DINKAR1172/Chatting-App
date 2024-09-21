package com.example.chattingapp.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chattingapp.Model.AuthRepository
import com.example.chattingapp.Model.Injection
import com.example.chattingapp.Model.Result
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class AuthViewModel():ViewModel(){
    private val _Repository:AuthRepository
    init {
        _Repository= AuthRepository(Store = Injection.instance(), FirebaseAuth.getInstance())
    }
    private val _authResult = MutableLiveData<Result<Boolean>>()
    val authResult: LiveData<com.example.chattingapp.Model.Result<Boolean>> get() = _authResult
    fun signUp(email: String, password: String, firstName: String, lastName: String) {
        viewModelScope.launch {
            _authResult.value =_Repository.SignUp(email, password, firstName, lastName)
        }
    }
    fun login(email: String, password: String) {
        viewModelScope.launch {
            _authResult.value = _Repository.login(email, password)
        }
    }
}