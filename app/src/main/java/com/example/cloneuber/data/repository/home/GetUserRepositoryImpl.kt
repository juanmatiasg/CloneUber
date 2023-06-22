package com.example.cloneuber.data.repository.home

import com.example.cloneuber.domain.model.Resource
import com.example.cloneuber.domain.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class GetUserRepositoryImpl(private val auth:FirebaseAuth,private val firestore: FirebaseFirestore) : GetUserRepository {

    override suspend fun getUserData(): Resource<User> {
        val currentUser = auth.currentUser
        // Realiza la consulta a Firestore para obtener los datos del usuario
        if(currentUser!= null) {

            val uid = currentUser.uid
            val userDoc = firestore.collection("users").document(uid).get().await()

            if( userDoc!=null && userDoc.exists() ) {
                val userData = userDoc.data
                // Obtén los datos del documento y crea una instancia de User
                val email = currentUser.email
                val name = userData?.get("name") as String
                val profileImage = userData["profileImage"] as String


                return Resource.Success(User(name = name, profileImage =  profileImage, email = email))
            }
            else{
                return Resource.Failure("Error al obtener los datos adicionales")
            }
        }
        else{
            return Resource.Failure("El usuario no esta autenticado")
        }
    }
}