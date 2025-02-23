package com.example.my_wallpapers.module

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth()  = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseFirestore()  = FirebaseFirestore.getInstance()
}