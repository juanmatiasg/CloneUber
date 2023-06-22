package com.example.cloneuber.data.repository.home

import com.example.cloneuber.domain.model.Resource
import com.example.cloneuber.domain.model.User

interface GetUserRepository {
    suspend fun getUserData(): Resource<User>
}