package com.example.cloneuber.domain.usecases

import com.example.cloneuber.domain.model.Resource
import com.example.cloneuber.data.repository.LoginRepository
import com.example.cloneuber.data.model.User

class LoginUseCase(private val loginRepository: LoginRepository) {
    suspend fun signIn(email: String, password: String):Resource<User>{
        return loginRepository.signIn(email, password)
    }
}