package com.app.whisperly.di

import android.app.Activity
import android.content.Context
import com.app.whisperly.view.MainActivity
import com.app.whisperly.view.navigation.MyRouteNavigator
import com.app.whisperly.view.navigation.RouteNavigator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class ViewModelModule {

    @Provides
    @ViewModelScoped
    fun bindRouteNavigator(): RouteNavigator = MyRouteNavigator()

}