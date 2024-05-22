package com.app.whisperly.database.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.app.whisperly.model.UserProfile
import kotlinx.parcelize.Parcelize


@Parcelize
@Entity
data class ChatUser(
    @PrimaryKey
    var id: String,
    val userName: String,
    val displayName: String,
    val user: UserProfile,
    val lastMessageTime: Long,
    val lastMessage:String,
    val unRead: Int = 0): Parcelable
