package com.example.testcomposeapp.domain

import com.example.testcomposeapp.data.Payee
import com.example.testcomposeapp.data.source.MobileBankingRepository

class GetPayeesUseCase(
    private val repository: MobileBankingRepository
) {
    suspend operator fun invoke(forceUpdate: Boolean = false): Result<List<Payee>> {
        return repository.getPayees(forceUpdate)
    }
}