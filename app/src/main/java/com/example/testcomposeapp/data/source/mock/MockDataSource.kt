package com.example.testcomposeapp.data.source.mock

import android.app.Application
import android.content.Context
import com.example.testcomposeapp.data.*
import com.google.gson.Gson
import com.vishnu.testapplication.data.*
import com.example.testcomposeapp.data.source.remote.MobileBankingApi
import org.koin.core.component.KoinComponent
import retrofit2.mock.BehaviorDelegate

class MockDataSource(val delegate: BehaviorDelegate<MobileBankingApi>, context: Context?) : MobileBankingApi, KoinComponent {

    val context = getKoin().get<Application>()

    override suspend fun login(request: LoginRequest): LoginResponse {
        val response = context.getMockResponse("mocks/login.json", LoginResponse::class.java)
        return delegate.returningResponse(response).login(request)
    }

    override suspend fun accountBalance(): AccountDetails {
        val response = context.getMockResponse("mocks/balance.json", AccountDetails::class.java)
        return delegate.returningResponse(response).accountBalance()
    }

    override suspend fun accountPayees(): Payees {
        val response = context.getMockResponse("mocks/payees.json", Payees::class.java)
        return delegate.returningResponse(response).accountPayees()
    }

    override suspend fun accountTransactions(): Transactions {
        val response = context.getMockResponse("mocks/transactions.json", Transactions::class.java)
        return delegate.returningResponse(response).accountTransactions()
    }

    override suspend fun transfer(request: TransferRequest): TransferResponse {
        val response = context.getMockResponse("mocks/transfer.json", TransferResponse::class.java)
        return delegate.returningResponse(response).transfer(request)
    }

}

fun <T> Context.getMockResponse(path: String, type: Class<T>): T {
    return assets.open(path).use { inputStream ->
        val mockText = inputStream.bufferedReader().use { it.readText() }
        val mockResponse = Gson().fromJson(mockText, type)
        mockResponse
    }
}