package com.app.whisperly.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

class HomeScreenActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomeScreenList()
        }
    }
}

@Composable
fun HomeScreenList() {
    Text(text = "List of chats will be shown here")
}