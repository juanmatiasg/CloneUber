package com.example.cloneuber.domain.usecases

import android.content.Context
import com.example.cloneuber.data.repository.signup.SignUpRepository
import com.example.cloneuber.domain.model.Resource
import com.example.cloneuber.domain.model.User

class SignUpUseCase(private val signUpRepository: SignUpRepository) {
    suspend fun signUp(name:String,email: String, password: String,type:String,profileImage:String): Resource<User> {
        return signUpRepository.signup(name,email, password,type,profileImage)
    }
}