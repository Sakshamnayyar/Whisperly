package com.app.whisperly.di

import android.content.Context
import androidx.room.Room
import com.app.whisperly.db.ChatUserDatabase
import com.app.whisperly.db.daos.ChatUserDao
import com.app.whisperly.db.daos.MessageDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideChatUserDatabase(
        @ApplicationContext context:Context): ChatUserDatabase {
        return Room.databaseBuilder(
            context,
            ChatUserDatabase::class.java,
            "chat_user_database"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideChatUserDao(database: ChatUserDatabase): ChatUserDao{
        return database.getChatUserDao()
    }

    @Singleton
    @Provides
    fun provideMessageDao(database: ChatUserDatabase): MessageDao{
        return database.getMessageDao()
    }

}