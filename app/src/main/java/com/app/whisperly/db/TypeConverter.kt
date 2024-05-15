package com.app.whisperly.db

import androidx.room.TypeConverter
import com.app.whisperly.db.data.AudioMessage
import com.app.whisperly.db.data.FileMessage
import com.app.whisperly.db.data.ImageMessage
import com.app.whisperly.db.data.TextMessage
import com.app.whisperly.db.data.VideoMessage
import com.app.whisperly.model.UserProfile
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TypeConverter {

    @TypeConverter
    fun fromUserProfiletoString(userProfile: UserProfile): String {
        return Gson().toJson(userProfile)
    }

    @TypeConverter
    fun fromStringToUserProfile(userProfileString: String): UserProfile {
        val type = object : TypeToken<UserProfile>() {}.type
        return Gson().fromJson(userProfileString,type)
    }

    @TypeConverter
    fun fromTextMessageToString(textMessage: TextMessage):String {
        return Gson().toJson(textMessage)
    }

    @TypeConverter
    fun fromStringToTextMessage(textMessageString: String): TextMessage {
        val type = object : TypeToken<TextMessage>() {}.type
        return Gson().fromJson(textMessageString,type)
    }

    @TypeConverter
    fun fromAudioMessageToString(audioMessage: AudioMessage):String {
        return Gson().toJson(audioMessage)
    }

    @TypeConverter
    fun fromStringToAudioMessage(audioMessageString: String): AudioMessage {
        val type = object : TypeToken<AudioMessage>() {}.type
        return Gson().fromJson(audioMessageString,type)
    }

    @TypeConverter
    fun fromVideoMessageToString(videoMessage: VideoMessage):String {
        return Gson().toJson(videoMessage)
    }

    @TypeConverter
    fun fromStringToVideoMessage(videoMessageString: String): VideoMessage {
        val type = object : TypeToken<VideoMessage>() {}.type
        return Gson().fromJson(videoMessageString,type)
    }

    @TypeConverter
    fun fromFileMessageToString(fileMessage: FileMessage):String {
        return Gson().toJson(fileMessage)
    }

    @TypeConverter
    fun fromStringToFileMessage(fileMessageString: String): FileMessage {
        val type = object : TypeToken<FileMessage>() {}.type
        return Gson().fromJson(fileMessageString,type)
    }

    @TypeConverter
    fun fromImageMessageToString(imageMessage: ImageMessage):String {
        return Gson().toJson(imageMessage)
    }

    @TypeConverter
    fun fromStringToImageMessage(imageMessageString: String): ImageMessage {
        val type = object : TypeToken<ImageMessage>() {}.type
        return Gson().fromJson(imageMessageString,type)
    }

}