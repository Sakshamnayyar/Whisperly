package com.app.whisperly.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserProfile (
    var uId: String? = null,
    var createdAt: Long? = null,
    var updatedAt: Long?=null,
    var image: String = "",
    var userName: String = "",
    var about: String = "",
    var token :String="",
    var mobile: ModelMobile? = null):Parcelable{
}