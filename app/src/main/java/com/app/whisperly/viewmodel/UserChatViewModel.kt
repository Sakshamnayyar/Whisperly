package com.app.whisperly.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.whisperly.database.data.ChatUser
import com.app.whisperly.repositories.DbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserChatViewModel @Inject constructor(
    private val dbRepo: DbRepository
): ViewModel() {

    private val list = MutableLiveData<ArrayList<ChatUser>>()
    private lateinit var chatUsers: List<ChatUser>
    init {
        CoroutineScope(Dispatchers.IO).launch {
            chatUsers = dbRepo.getChatUserList()
        }
    }

    fun addUser(phoneNumber: String, name: String) {
        var user =
        CoroutineScope(Dispatchers.IO).launch {
//            dbRepo.insertUser(user)
        }
    }

    fun getUserList() = dbRepo.getAllUsersOrderByLastMessage()
}