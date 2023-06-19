package com.example.cloneuber.data.repository.signin

import com.example.cloneuber.domain.model.User
import com.example.cloneuber.domain.model.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await

class SignInRepositoryImpl(private val auth: FirebaseAuth) : SignInRepository {

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