package com.app.whisperly.db


import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.app.whisperly.db.daos.ChatUserDao
import com.app.whisperly.db.daos.MessageDao
import com.app.whisperly.db.data.ChatUser
import com.app.whisperly.db.data.Message

@Database(entities = [ChatUser::class, Message::class], version = 1, exportSchema = false)
@TypeConverters(TypeConverter::class)
abstract class ChatUserDatabase : RoomDatabase() {
    abstract fun getChatUserDao(): ChatUserDao
    abstract fun getMessageDao(): MessageDao
}
