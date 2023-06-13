package com.example.cloneuber.data.repository

import com.example.cloneuber.data.model.User
import com.example.cloneuber.domain.model.Resource
import com.google.firebase.auth.AuthResult

interface LoginRepository {
    suspend fun signIn(email: String, password: String):Resource<User>
}