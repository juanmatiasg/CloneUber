package com.example.cloneuber.domain.usecases

import com.example.cloneuber.data.repository.logout.LogoutRepository

class LogoutUseCase(private val logoutRepository: LogoutRepository) {
    suspend operator fun invoke() {
        logoutRepository.logout()
    }

}