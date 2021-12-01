package com.example.testcomposeapp.domain.util

import com.google.gson.Gson

fun <T> T.json(): String = Gson().toJson(this)