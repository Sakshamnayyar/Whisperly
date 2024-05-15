package com.app.whisperly.view.homescreen

import android.content.Context
import androidx.lifecycle.ViewModel
import com.app.whisperly.db.data.ChatUser
import com.app.whisperly.db.repository.DbRepository
import com.app.whisperly.model.ModelMobile
import com.app.whisperly.model.UserProfile
import com.app.whisperly.view.navigation.RouteNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val routeNavigator: RouteNavigator,
    private val dbRepository: DbRepository
) : ViewModel(), RouteNavigator by routeNavigator {

    val chatUserList = mutableListOf<ChatUser>()

    init {
        fetchChats()
    }

    private fun fetchChats() {

        chatUserList.add(ChatUser(
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
            unRead = 0))

        chatUserList.add(ChatUser(
            id = "two",
            userName = "Nayyar",
            displayName = "Nayyar",
            user = UserProfile(
                uId = "two",
                createdAt = System.currentTimeMillis(),
                userName = "Nayyar",
                mobile = ModelMobile("+1","3023903883"),),
            lastMessageTime = 0L,
            lastMessage = "Bhai!!",
            unRead = 0))

    }

}