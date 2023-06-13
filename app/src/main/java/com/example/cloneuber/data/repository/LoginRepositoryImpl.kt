package com.example.cloneuber.data.repository

import com.example.cloneuber.data.model.User
import com.example.cloneuber.domain.model.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await

class LoginRepositoryImpl(private val auth: FirebaseAuth) : LoginRepository {

    override suspend fun signIn(email: String, password: String): Resource<User> {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            val user = mapFirebaseUserToUser(result.user!!)
            Resource.Success(user)
        } catch (e: Exception) {
            Resource.Failure(e.message ?: "An error occurred")
        }
    }
    private fun mapFirebaseUserToUser(firebaseUser: FirebaseUser): User {
        return User(firebaseUser.uid, firebaseUser.email ?: "", "")
    }


}