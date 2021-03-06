package com.example.testcomposeapp.data.source

import com.example.testcomposeapp.data.*
import com.vishnu.testapplication.data.*
import com.example.testcomposeapp.data.mapper.AccountMapper
import com.example.testcomposeapp.data.mapper.PayeesMapper
import com.example.testcomposeapp.data.mapper.TransactionsMapper
import com.example.testcomposeapp.data.source.local.LocalMobileBankingDataSource
import com.example.testcomposeapp.data.source.remote.RemoteMobileBankingDataSource
import com.example.testcomposeapp.di.io
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.example.testcomposeapp.domain.Result

class DefaultDataSource(
    private val localDataSource: LocalMobileBankingDataSource,
    private val remoteDataSource: RemoteMobileBankingDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.io
) : MobileBankingRepository {

    override suspend fun invalidate() {
        localDataSource.clear()
    }

    override suspend fun login(request: LoginRequest): Result<LoginResponse> {
        return withContext(ioDispatcher) {
            remoteDataSource.login(request)
        }
    }

    override suspend fun getAccountDetails(forceUpdate: Boolean): Result<AccountSummary> {
        val cacheResponse = localDataSource.accountDetails()

        return if (cacheResponse == null || forceUpdate) {
            withContext(ioDispatcher) {
                val balance = remoteDataSource.accountDetails()
                when (balance) {
                    is Result.Success -> {
                        val summary = AccountMapper.map(balance.data).also {
                            localDataSource.cacheAccountDetails(it)
                        }
                        Result.Success(summary)
                    }
                    is Result.Loading -> Result.Loading
                    is Result.Error -> Result.Error(balance.exception)
                }
            }
        } else {
            Result.Success(cacheResponse)
        }
    }

    override suspend fun getTransactions(forceUpdate: Boolean): Result<List<TransactionSummary>> {
        val cacheResponse = localDataSource.transactionsList()

        return if (cacheResponse == null || forceUpdate) {
            withContext(ioDispatcher) {
                val transactions = remoteDataSource.transactionsList()
                when (transactions) {
                    is Result.Success -> {
                        val transactionsSummary = transactions.data.transactions.map {
                            TransactionsMapper.map(it)
                        }.also {
                            localDataSource.cacheTransactionsList(it)
                        }
                        Result.Success(transactionsSummary)
                    }
                    is Result.Loading -> Result.Loading
                    is Result.Error -> {
                        localDataSource.clearTransactionsList()
                        Result.Error(transactions.exception)
                    }
                }
            }
        } else {
            Result.Success(cacheResponse)
        }
    }

    override suspend fun getPayees(forceUpdate: Boolean): Result<List<Payee>> {
        val cacheResponse = localDataSource.payeesList()

        return if (cacheResponse == null || forceUpdate) {
            withContext(ioDispatcher) {
                val payees = remoteDataSource.payeesList()
                when (payees) {
                    is Result.Success -> {
                        val payeesList = payees.data.let {
                            PayeesMapper.map(it)
                        }.also {
                            localDataSource.cachePayeesList(it)
                        }
                        Result.Success(payeesList)
                    }
                    is Result.Loading -> Result.Loading
                    is Result.Error -> {
                        localDataSource.clearPayeesList()
                        Result.Error(payees.exception)
                    }
                }
            }
        } else {
            Result.Success(cacheResponse)
        }
    }

    /**
     * For Transfer, no need to cache, as there is no use of this.
     */
    override suspend fun transfer(request: TransferRequest): Result<TransferResponse> {
        return withContext(ioDispatcher) {
            remoteDataSource.transfer(request)
        }
    }

}