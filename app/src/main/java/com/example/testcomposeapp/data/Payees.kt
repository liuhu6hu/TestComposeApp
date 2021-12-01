package com.example.testcomposeapp.data

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Payees(
    @SerializedName("data") val payees: List<Payee>?,
    val status: String
)