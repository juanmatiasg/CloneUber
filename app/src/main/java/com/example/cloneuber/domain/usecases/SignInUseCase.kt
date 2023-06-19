package com.example.cloneuber.domain.usecases

import com.example.cloneuber.domain.model.Resource
import com.example.cloneuber.data.repository.signin.SignInRepository
import com.example.cloneuber.domain.model.User

class SignInUseCase(private val loginRepository: SignInRepository) {
    suspend fun signIn(email: String, password: String):Resource<User>{
        return loginRepository.signIn(email, password)
    }
}