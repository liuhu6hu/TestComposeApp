package com.example.testcomposeapp

import android.app.Application
import com.example.testcomposeapp.di.coroutinesModule
import com.example.testcomposeapp.di.databaseModule
import com.example.testcomposeapp.di.launchModule
import com.example.testcomposeapp.di.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MobileBankApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MobileBankApplication)
            modules(launchModule)
            modules(repositoryModule, databaseModule)
            modules(coroutinesModule)
        }
    }
}