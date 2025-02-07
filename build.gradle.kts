buildscript {
    dependencies {
        classpath("com.google.gms:google-services:4.4.1")
        classpath("com.android.tools.build:gradle:7.0.3")
        classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.23")
        classpath ("com.google.dagger:hilt-android-gradle-plugin:2.51")

    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.2.1" apply false
    id("org.jetbrains.kotlin.android") version "1.9.23" apply false
    id("com.google.gms.google-services") version "4.4.1" apply false
    id("com.google.dagger.hilt.android") version "2.51" apply false
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.0" apply false
    id("com.google.devtools.ksp") version "1.9.23-1.0.19" apply false
    id("com.android.library") version "8.2.1" apply false
}