package com.app.whisperly.util

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import com.app.whisperly.db.data.ChatUser
import com.app.whisperly.model.ModelMobile
import com.app.whisperly.model.UserProfile

object  UserUtil {
    fun fetchChatUser(phoneNumber: String, name: String){

        val user = ChatUser(
            id = "one",
            userName = "Saksham",
            displayName = "Saksham",
            user = UserProfile(
                uId = "one",
                createdAt = System.currentTimeMillis(),
                userName = "Saksham",
                mobile = ModelMobile("+91","9855430405"),),
            lastMessageTime = 0L,
            lastMessage = "Hello",
            unRead = 0)

    }

    fun getModelMobile(phoneNumber: String){

    }

    fun  getCountryCodeRemoved() {

    }


}
