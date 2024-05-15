package com.app.whisperly.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.whisperly.db.data.Message
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(message: Message)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMultipleMessage(users: List<Message>)

    @Query("SELECT * FROM Message")
    fun getAllMessages(): Flow<List<Message>>

    @Query("SELECT * FROM Message")
    fun getMessageList(): List<Message>

    @Query("SELECT * FROM Message WHERE `chatUserId`=:chatUserId")
    fun getChatsOfFriend(chatUserId: String): List<Message>

    @Query("DELETE FROM Message  WHERE createdAt=:createdAt")
    suspend fun deleteMessageByCreatedAt(createdAt: Long)

    @Query("DELETE FROM Message WHERE `to`=:userId")
    suspend fun deleteMessagesByUserId(userId: String)

    @Query("DELETE FROM Message")
    fun nukeTable()

}