package com.example.shoppingapp.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

//입력시 앱 모듈클릭?
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton  //전체 앱에 걸쳐 하나의 인스턴스만 생성
    fun provideFirebaseAuth() = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseFirestoreDatabase() = Firebase.firestore

}