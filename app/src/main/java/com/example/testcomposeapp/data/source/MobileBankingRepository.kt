package com.example.testcomposeapp.data.source

import com.example.testcomposeapp.data.*
import com.vishnu.testapplication.data.*
import com.example.testcomposeapp.domain.Result

interface MobileBankingRepository {
    suspend fun invalidate()
    suspend fun login(request: LoginRequest): Result<LoginResponse>
    suspend fun getAccountDetails(forceUpdate: Boolean): Result<AccountSummary>
    suspend fun getTransactions(forceUpdate: Boolean): Result<List<TransactionSummary>>
    suspend fun getPayees(forceUpdate: Boolean): Result<List<Payee>>
    suspend fun transfer(request: TransferRequest): Result<TransferResponse>
}