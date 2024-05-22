package com.app.whisperly.database.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Message(
    @PrimaryKey
    val id: Long = 0L,
    var chatUserId: String? = null,
    var createdAt: Long = 0L,
    var deliveryTime: Long = 0L,
    var seenTime: Long = 0L,
    val from: String,
    val to: String,
    val senderName: String,
    val senderImage: String,
    var type: String = "text",//0=text,1=audio,2=image,3=video,4=file
    var status: Int = 0,//0=sending,1=sent,2=delivered,3=seen,4=failed
    var textMessage: TextMessage?=null,
    var imageMessage: ImageMessage?=null,
    var audioMessage: AudioMessage?=null,
    var videoMessage: VideoMessage?=null,
    var fileMessage: FileMessage?=null,): Parcelable


@Parcelize
data class TextMessage(val text: String?=null): Parcelable

@Parcelize
data class AudioMessage(var uri: String?=null,val duration: Int=0): Parcelable

@Parcelize
data class ImageMessage(var uri: String?=null,var imageType: String="image"): Parcelable

@Parcelize
data class VideoMessage(val uri: String?=null,val duration: Int=0): Parcelable

@Parcelize
data class FileMessage(val name: String?=null,
                       val uri: String?=null,val duration: Int=0): Parcelable