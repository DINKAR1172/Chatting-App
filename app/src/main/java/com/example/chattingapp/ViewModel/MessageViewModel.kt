package com.example.chattingapp.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chattingapp.Model.AuthRepository
import com.example.chattingapp.Model.Injection
import com.example.chattingapp.Model.Message
import com.example.chattingapp.Model.MessageRepository
import com.example.chattingapp.Model.Result
import com.example.chattingapp.Model.User
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class MessageViewModel:ViewModel() {
    private val _messages = MutableLiveData<List<Message>>()
    val messages: LiveData<List<Message>> get() = _messages

    private val _roomId = MutableLiveData<String>()
    private val _currentUser = MutableLiveData<User>()
    val currentUser: LiveData<User> get() = _currentUser
    private val messageRepository: MessageRepository
    private val userRepository: AuthRepository
    init {
        messageRepository = MessageRepository(Injection.instance())
        userRepository = AuthRepository( Injection.instance(),FirebaseAuth.getInstance())
        loadCurrentUser()
    }
    private fun loadCurrentUser() {
        viewModelScope.launch {
            when (val result = userRepository.getCurrentUser()) {
                is Result.Success -> _currentUser.value = result.data
                is Error -> {
                }

                else -> {}
            }
        }
    }
    fun loadMessages() {
        viewModelScope.launch {
            if (_roomId != null) {
                messageRepository.getChatMessages(_roomId.value.toString())
                    .collect { _messages.value = it }
            }
        }
    }
    fun sendMessage(text: String) {
        if (_currentUser.value != null) {
            val message = Message(
                senderFirstName = _currentUser.value!!.FirstNAme,
                senderId = _currentUser.value!!.Email,
                text = text
            )
            viewModelScope.launch {
                when (messageRepository.sendMessage(_roomId.value.toString(), message)) {
                    is Result.Success -> Unit
                    is Error -> {

                    }

                    else -> {}
                }
            }
        }
    }
    fun setRoomId(roomId: String) {
        _roomId.value = roomId
        loadMessages()
    }
}