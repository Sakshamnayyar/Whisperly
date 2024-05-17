package com.app.whisperly.view.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.app.whisperly.view.homescreen.HomeScreenRoute
import com.app.whisperly.view.homescreen.HomeScreenViewModel
import com.app.whisperly.view.profileScreen.ProfileScreenRoute
import com.app.whisperly.view.profileScreen.ProfileViewModel
import com.app.whisperly.view.signin.SignInRoute
import com.app.whisperly.view.signin.SignInViewModel
import com.app.whisperly.view.signin.VerifyRoute

@Composable
fun NavigationComponent(navHostController: NavHostController, paddingValues: PaddingValues) {

    val signInViewModel: SignInViewModel = hiltViewModel()
    val homeScreenViewModel: HomeScreenViewModel = hiltViewModel()
    val profileViewModel:ProfileViewModel = hiltViewModel()

    NavHost(
        navController = navHostController,
        startDestination = SignInRoute.route,
        modifier = Modifier.padding(paddingValues)
    ) {
        SignInRoute.composable(this, navHostController,signInViewModel)
        VerifyRoute.composable(this,navHostController,signInViewModel)
        HomeScreenRoute.composable(this, navHostController, homeScreenViewModel)
        ProfileScreenRoute.composable(this,navHostController,profileViewModel)
        //Define all the routes for screens here.
    }
}