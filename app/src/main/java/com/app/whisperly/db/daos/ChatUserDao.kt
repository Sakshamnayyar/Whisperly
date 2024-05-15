package com.app.whisperly.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.app.whisperly.db.data.ChatUser
import kotlinx.coroutines.flow.Flow

@Dao
interface ChatUserDao {

    @Insert
    suspend fun insertUser(chatUser: ChatUser)

    @Insert
    suspend fun insertMultipleUser(users: List<ChatUser>)

    @Query("SELECT * FROM ChatUser ORDER BY displayName")
    suspend fun getAllUsers(): List<ChatUser>

    @Query("SELECT * FROM ChatUser ORDER BY lastMessageTime")
    fun getAllUsersOrderbyLastMessage(): Flow<List<ChatUser>?>

}