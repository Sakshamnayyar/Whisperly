package com.app.whisperly.view.profileScreen

import android.content.Context
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.whisperly.model.UserProfile
import com.app.whisperly.util.MPreference
import com.app.whisperly.util.UserUtil
import com.app.whisperly.util.toast
import com.app.whisperly.view.navigation.RouteNavigator
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.firestore.SetOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val preference: MPreference,
    private val routeNavigator: RouteNavigator
) : ViewModel(), RouteNavigator by routeNavigator {

    val name = MutableLiveData("")

    var profilePicUrl = MutableLiveData("")

    private var about = ""

    private var createdAt: Long = System.currentTimeMillis()

    private val storageRef = UserUtil.getStorageRef(context)

    private val docuRef = UserUtil.getDocumentRef(context)

    init {
        val userProfile = preference.getUserProfile()
        userProfile?.let {
            name.value = userProfile.userName
            profilePicUrl.value = userProfile.image
            about = userProfile.about
            createdAt = userProfile.createdAt ?: System.currentTimeMillis()
        }
    }

    fun uploadProfileImage(imagePath: Uri) {
        try {
            val child = storageRef.child("profile_picture_${System.currentTimeMillis()}.jpg")
            val task = child.putFile(imagePath)
            task.addOnSuccessListener {
                child.downloadUrl.addOnCompleteListener { taskResult ->
                    profilePicUrl.value = taskResult.result.toString()
                }.addOnFailureListener {
                    OnFailureListener { e ->
                        context.toast(e.message.toString())
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun storeProfileData() {
        try {
            val profile = UserProfile(
                uId = preference.getUid()!!,
                createdAt = createdAt,
                updatedAt = System.currentTimeMillis(),
                image = profilePicUrl.value!!,
                userName = name.value!!.lowercase(),
                about = about,
                token = preference.getPushToken().toString(),
                mobile = preference.getMobile()
            )
            docuRef.set(profile, SetOptions.merge()).addOnSuccessListener {
                preference.saveProfile(profile)
            }.addOnFailureListener { e ->
                context.toast(e.message.toString())
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}