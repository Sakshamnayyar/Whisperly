package com.app.whisperly.util


import android.content.Context
import com.app.whisperly.db.data.ChatUser
import com.app.whisperly.model.ModelMobile
import com.app.whisperly.model.UserProfile
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage

object  UserUtil {
    fun fetchChatUser(phoneNumber: String, name: String): List<ChatUser>{

        return listOf(ChatUser(
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
            unRead = 0),
            ChatUser(
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

    fun getStorageRef(context: Context): StorageReference {
        val preference = MPreference(context)
        val ref = Firebase.storage.getReference("Users")
        return ref.child(preference.getUid().toString())
    }

    fun getDocumentRef(context: Context): DocumentReference {
        val preference = MPreference(context)
        val db = FirebaseFirestore.getInstance()
        return db.collection("Users").document(preference.getUid()!!)
    }

}
