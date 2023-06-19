package com.example.cloneuber.data.repository.signup

import com.example.cloneuber.domain.model.Resource
import com.example.cloneuber.domain.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class SignUpRepositoryImpl(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : SignUpRepository {
    override suspend fun signup(name:String,email: String, password: String, type: String,profileImage:String): Resource<User> {
        return try {
            val authResult = auth.createUserWithEmailAndPassword(email, password).await()
            val userId = authResult.user?.uid

            // Guardar los detalles del usuario en Firestore
            if(userId!=null){

                val user = User(id = userId,name=name, email = email, type = type, profileImage = profileImage)
                val userMapNotNull = user.toMapWithoutNulls() // Eliminar campos nulos del objeto user

                firestore.collection("users").document(userId).set(userMapNotNull).await()
            }

            val user = User(id = userId,name = name, email = email, type = type, profileImage = profileImage) // Crea el objeto User con el ID de usuario y el email
            Resource.Success(user)

        } catch (e: Exception) {
            Resource.Failure(e.message ?: "An error occurred")
        }
    }

    // Extensión en la clase User para crear un mapa sin campos nulos
    fun User.toMapWithoutNulls(): Map<String, Any> {
        val userMap = mutableMapOf<String, Any>()
        val fields = User::class.java.declaredFields

        for (field in fields) {
            field.isAccessible = true
            val value = field.get(this)
            if (value != null) {
                userMap[field.name] = value
            }
        }

        return userMap
    }


}