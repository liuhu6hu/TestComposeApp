package com.example.testcomposeapp.domain

import com.example.testcomposeapp.data.source.MobileBankingRepository

class DeleteCacheUseCase(
    private val repository: MobileBankingRepository
) {
    suspend operator fun invoke() {
        repository.invalidate()
    }
}