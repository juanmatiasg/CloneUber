package com.example.cloneuber.data.repository.signup

import com.example.cloneuber.domain.model.Resource
import com.example.cloneuber.domain.model.User

interface SignUpRepository {
    suspend fun signup(name:String,email:String,password:String,type:String,profileImage:String): Resource<User>
}