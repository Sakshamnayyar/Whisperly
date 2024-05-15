package com.app.whisperly.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Scaffold
import androidx.navigation.compose.rememberNavController
import com.app.whisperly.ui.theme.WhisperlyTheme
import com.app.whisperly.view.navigation.NavigationComponent
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.auth
import dagger.Provides
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


private const val TAG: String = "MainActivity"


@AndroidEntryPoint
class MainActivity @Inject constructor(): ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)

        setContent {
            val navController = rememberNavController()
            WhisperlyTheme {
                Scaffold {
                    NavigationComponent(navHostController = navController, paddingValues = it)
                }
            }
        }
    }
}


