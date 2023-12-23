
buildscript {
    dependencies {
        classpath ("com.google.dagger:hilt-android-gradle-plugin:2.40.1")

//        def nav_version = "2.5.0"
        classpath ("androidx.navigation:navigation-safe-args-gradle-plugin:2.5.0")
        classpath("com.google.gms:google-services:4.4.0")
    }
}


plugins {
    id("com.android.application") version "8.1.1" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id("com.google.dagger.hilt.android") version "2.44" apply false
}
