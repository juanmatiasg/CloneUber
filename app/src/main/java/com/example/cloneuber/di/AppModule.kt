package com.example.cloneuber.di

import com.example.cloneuber.data.repository.LoginRepository
import com.example.cloneuber.data.repository.LoginRepositoryImpl
import com.example.cloneuber.domain.usecases.LoginUseCase
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    fun provideUserRepository(auth: FirebaseAuth): LoginRepository = LoginRepositoryImpl(auth)

    @Provides
    fun provideLoginUseCase(loginRepository: LoginRepository): LoginUseCase = LoginUseCase(loginRepository)

}