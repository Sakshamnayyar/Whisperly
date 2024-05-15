package com.app.whisperly.view.homescreen

import android.content.Context
import androidx.lifecycle.ViewModel
import com.app.whisperly.db.data.ChatUser
import com.app.whisperly.db.repository.DbRepository
import com.app.whisperly.model.ModelMobile
import com.app.whisperly.model.UserProfile
import com.app.whisperly.util.UserUtil
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
        chatUserList.addAll(UserUtil.fetchChatUser("",""))
    }

}