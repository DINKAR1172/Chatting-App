package com.example.chattingapp.ViewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chattingapp.Model.Injection
import com.example.chattingapp.Model.Result
import com.example.chattingapp.Model.Room
import com.example.chattingapp.Model.RoomRepository
import kotlinx.coroutines.launch

class RoomViewModel():ViewModel() {
    private val _RoomID= mutableStateOf("")
    val RoomId:State<String> =_RoomID
    fun SetRoomId(id:String){
      _RoomID.value=id
    }
    private val _rooms = MutableLiveData<List<Room>>()
    val rooms: LiveData<List<Room>> get() = _rooms
    private val roomRepository: RoomRepository
    init {
        roomRepository = RoomRepository(Injection.instance())
        loadRooms()
    }

    fun createRoom(name: String) {
        viewModelScope.launch {
            roomRepository.createRoom(name)
        }
    }

    fun loadRooms() {
        viewModelScope.launch {
            when (val result = roomRepository.getRooms()) {
                is Result.Success -> _rooms.value = result.data
                is Error -> {

                }

                else -> {}
            }
        }
    }
}