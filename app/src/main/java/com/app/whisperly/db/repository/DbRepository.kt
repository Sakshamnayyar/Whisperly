package com.app.whisperly.db.repository

import com.app.whisperly.db.daos.ChatUserDao
import com.app.whisperly.db.daos.MessageDao
import com.app.whisperly.db.data.ChatUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class DbRepository @Inject constructor(
    private val userDao: ChatUserDao,
    private val messageDao: MessageDao
) {

    fun insertUser(user: ChatUser) {
        CoroutineScope(Dispatchers.IO).launch {
            userDao.insertUser(user)
        }
    }

    fun insertMultipleUser(users: List<ChatUser>) {
        CoroutineScope(Dispatchers.IO).launch {
            userDao.insertMultipleUser(users)
        }
    }

    suspend fun getChatUserList() = userDao.getAllUsers()

    fun getAllUsersOrderByLastMessage() = userDao.getAllUsersOrderbyLastMessage()

    suspend fun deleteChatUserById(user: ChatUser) {
    }
}