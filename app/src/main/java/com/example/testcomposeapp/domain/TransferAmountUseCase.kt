package com.example.testcomposeapp.domain

import com.example.testcomposeapp.data.TransferRequest
import com.example.testcomposeapp.data.TransferResponse
import com.example.testcomposeapp.data.source.MobileBankingRepository

class TransferAmountUseCase(
    private val repository: MobileBankingRepository
) {
    suspend operator fun invoke(request: TransferRequest): Result<TransferResponse> {
        return repository.transfer(request)
    }
}