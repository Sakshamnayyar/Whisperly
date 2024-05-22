package com.app.whisperly.database


import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.app.whisperly.database.daos.ChatUserDao
import com.app.whisperly.database.daos.MessageDao
import com.app.whisperly.database.data.ChatUser
import com.app.whisperly.database.data.Message
import com.app.whisperly.datasource.TypeConverter

@Database(entities = [ChatUser::class, Message::class], version = 1, exportSchema = false)
@TypeConverters(TypeConverter::class)
abstract class ChatUserDatabase : RoomDatabase() {
    abstract fun getChatUserDao(): ChatUserDao
    abstract fun getMessageDao(): MessageDao
}
