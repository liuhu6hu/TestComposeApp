package com.example.testcomposeapp.domain


import com.example.testcomposeapp.data.source.MobileBankingRepository
import com.example.testcomposeapp.di.sessionHelper

class InvalidateSessionUseCase(
    private val repository: MobileBankingRepository
) {
    suspend operator fun invoke() {
        sessionHelper.refresh()
        repository.invalidate()
    }
}