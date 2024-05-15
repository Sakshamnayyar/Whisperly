package com.app.whisperly.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Country(
    val code: String, val name: String, val noCode: String,
    val money: String
) : Parcelable
