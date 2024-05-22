package com.app.whisperly.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ModelMobile(
    var country: String = "",
    var phoneNumber: String = ""
): Parcelable