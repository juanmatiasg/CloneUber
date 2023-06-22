package com.example.cloneuber.data.repository.logout

import com.google.firebase.auth.FirebaseAuth

class LogoutRepositoryImpl(private val auth:FirebaseAuth):LogoutRepository {
    override suspend fun logout() { auth.signOut() }
}