package com.example.testcomposeapp.domain

import com.example.testcomposeapp.data.AccountSummary
import com.example.testcomposeapp.data.source.MobileBankingRepository
import com.example.testcomposeapp.domain.util.toCurrencyAmount


class GetAccountDetailsUseCase(
    private val repository: MobileBankingRepository
) {
    suspend operator fun invoke(forceUpdate: Boolean = false): Result<AccountSummary> {
        return repository.getAccountDetails(forceUpdate).let {
            if (it is Result.Success) {
                it.data.balance = it.data.balance.toCurrencyAmount()
            }
            it
        }
    }
}