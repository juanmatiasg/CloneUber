package com.example.cloneuber.data.repository.signin

import com.example.cloneuber.domain.model.User
import com.example.cloneuber.domain.model.Resource

interface SignInRepository {
    suspend fun signIn(email: String, password: String):Resource<User>
}