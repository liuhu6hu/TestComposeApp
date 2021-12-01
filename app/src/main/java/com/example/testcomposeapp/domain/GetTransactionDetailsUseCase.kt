package com.example.testcomposeapp.domain

import com.example.testcomposeapp.data.TransactionSummary
import com.example.testcomposeapp.data.source.MobileBankingRepository

class GetTransactionDetailsUseCase(
    private val repository: MobileBankingRepository
) {
    suspend operator fun invoke(forceUpdate: Boolean = false): Result<List<TransactionSummary>> {
        val result = repository.getTransactions(forceUpdate)
        return if (result is Result.Success) {
            val data = result.data
            val groupedData = data.groupBy { it.date }.map {
                val date = TransactionSummary(date = it.key)
                val items = it.value.map { transaction -> transaction.date = ""; transaction }.toTypedArray()
                listOf(date, *items)
            }.flatten()
            Result.Success(groupedData)
        } else {
            result
        }
    }
}