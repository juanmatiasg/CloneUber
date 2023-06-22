package com.example.cloneuber.di

import com.example.cloneuber.ui.signin.SignInActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class,])
interface AppComponent {
    fun inject(signInActvity: SignInActivity)
}