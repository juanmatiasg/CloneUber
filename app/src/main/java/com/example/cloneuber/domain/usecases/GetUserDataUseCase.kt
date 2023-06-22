package com.example.cloneuber.domain.usecases

import com.example.cloneuber.data.repository.home.GetUserRepository
import com.example.cloneuber.domain.model.Resource
import com.example.cloneuber.domain.model.User

class GetUserDataUseCase(private val getUserRepository: GetUserRepository) {
    suspend fun fetchGetUserData(): Resource<User> = getUserRepository.getUserData()
}