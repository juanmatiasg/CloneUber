package com.example.cloneuber.di

import android.app.Application
import android.content.Context
import com.example.cloneuber.data.repository.home.GetUserRepository
import com.example.cloneuber.data.repository.home.GetUserRepositoryImpl
import com.example.cloneuber.data.repository.logout.LogoutRepository
import com.example.cloneuber.data.repository.logout.LogoutRepositoryImpl
import com.example.cloneuber.data.repository.signin.SignInRepository
import com.example.cloneuber.data.repository.signin.SignInRepositoryImpl
import com.example.cloneuber.data.repository.signup.SignUpRepository
import com.example.cloneuber.data.repository.signup.SignUpRepositoryImpl
import com.example.cloneuber.domain.usecases.GetUserDataUseCase
import com.example.cloneuber.domain.usecases.LogoutUseCase
import com.example.cloneuber.domain.usecases.SignInUseCase
import com.example.cloneuber.domain.usecases.SignUpUseCase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
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
    fun provideFirebastore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    fun provideApplicationContext(application: Application): Context { return application.applicationContext}


    //Sign In
    @Provides
    fun provideSignInRepository(auth: FirebaseAuth): SignInRepository = SignInRepositoryImpl(auth)

    @Provides
    fun provideSignInUseCase(signInRepository: SignInRepository): SignInUseCase = SignInUseCase(signInRepository)

    //Sign Up
    @Provides
    fun provideSignUpRepository(auth: FirebaseAuth,firestore: FirebaseFirestore):SignUpRepository = SignUpRepositoryImpl(auth,firestore)

    @Provides
    fun provideSignUpUseCase(signUpRepository: SignUpRepository):SignUpUseCase = SignUpUseCase(signUpRepository)


    //Home Activity
    @Provides
    fun provideGetUserUserRepository(auth:FirebaseAuth,firestore: FirebaseFirestore):GetUserRepository  = GetUserRepositoryImpl(auth,firestore)

    @Provides
    fun provideGetUserDataUseCase(getUserRepository: GetUserRepository): GetUserDataUseCase = GetUserDataUseCase(getUserRepository)



    @Provides
    fun provideLogoutRepository(auth:FirebaseAuth):LogoutRepository  = LogoutRepositoryImpl(auth)

    @Provides
    fun provideLogoutUseCase(logoutRepository: LogoutRepository): LogoutUseCase = LogoutUseCase(logoutRepository)




}