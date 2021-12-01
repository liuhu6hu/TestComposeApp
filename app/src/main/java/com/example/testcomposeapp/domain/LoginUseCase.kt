package com.example.testcomposeapp.domain

import com.vishnu.testapplication.data.*
import com.example.testcomposeapp.data.source.MobileBankingRepository
import com.example.testcomposeapp.di.sessionHelper

class LoginUseCase(private val repository: MobileBankingRepository) {

    suspend operator fun invoke(request: LoginRequest): Result<LoginResponse> {
        val result = repository.login(request)
        if (result.succeeded) {
            sessionHelper.setApiToken(result.data?.token.orEmpty())
        }
        return result
    }
}