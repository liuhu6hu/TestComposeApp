package com.example.testcomposeapp.data.source.remote

import com.example.testcomposeapp.data.*
import com.vishnu.testapplication.data.*
import kotlinx.coroutines.delay
import com.example.testcomposeapp.domain.Result

class RemoteMobileBankingDataSource(private val api: MobileBankingApi) {

    suspend fun login(request: LoginRequest): Result<LoginResponse> {
        return try {
            addDelaySinceMockServerReturnsVeryFast()
            val response = api.login(request)
            Result.Success(response)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    suspend fun accountDetails(): Result<AccountDetails> {
        return try {
            addDelaySinceMockServerReturnsVeryFast()
            val response = api.accountBalance()
            Result.Success(response)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    suspend fun transactionsList(): Result<Transactions> {
        return try {
            addDelaySinceMockServerReturnsVeryFast()
            val response = api.accountTransactions()
            Result.Success(response)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    suspend fun payeesList(): Result<Payees> {
        return try {
            addDelaySinceMockServerReturnsVeryFast()
            val response = api.accountPayees()
            Result.Success(response)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    suspend fun transfer(request: TransferRequest): Result<TransferResponse> {
        return try {
            addDelaySinceMockServerReturnsVeryFast()
            val response = api.transfer(request)
            Result.Success(response)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    private suspend fun addDelaySinceMockServerReturnsVeryFast() {
        delay(2000)     // Add delay as Node Server returns very quick
    }
}