package com.example.testcomposeapp.data.source

import com.example.testcomposeapp.data.BankingApiClient
import com.example.testcomposeapp.data.source.remote.MobileBankingApi

fun getApiClient(): MobileBankingApi {
    return BankingApiClient.createMock()
}