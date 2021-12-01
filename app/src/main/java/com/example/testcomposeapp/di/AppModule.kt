package com.example.testcomposeapp.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.example.testcomposeapp.data.source.DefaultDataSource
import com.example.testcomposeapp.data.source.MobileBankingRepository
import com.example.testcomposeapp.data.source.getApiClient
import com.example.testcomposeapp.domain.OnBoardingCompleteUseCase
import com.example.testcomposeapp.domain.OnBoardingCompletedUseCase
import com.example.testcomposeapp.data.source.local.LocalMobileBankingDataSource
import com.example.testcomposeapp.data.source.local.SessionDatabase
import com.example.testcomposeapp.data.source.local.SessionHelper
import com.example.testcomposeapp.data.source.remote.RemoteMobileBankingDataSource
import com.example.testcomposeapp.domain.DeleteCacheUseCase
import com.example.testcomposeapp.domain.LoginUseCase
import com.example.testcomposeapp.ui.login.LaunchViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainCoroutineDispatcher
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent.getKoin


val repositoryModule = module {
    single { LocalMobileBankingDataSource() }
    factory { DeleteCacheUseCase(get()) }
    single { getApiClient() }
    single { RemoteMobileBankingDataSource(get()) }
    single<MobileBankingRepository> { DefaultDataSource(get(), get(), Dispatchers.io) }
    single<SharedPreferences> {
        androidApplication().getSharedPreferences(
            "AppSharedPreferences",
            Context.MODE_PRIVATE
        )
    }
}

val launchModule = module {
    factory { OnBoardingCompleteUseCase(get()) }
    factory { OnBoardingCompletedUseCase(get()) }
    factory { LoginUseCase(get()) }
    viewModel { LaunchViewModel(get(), get(), get(), get(named("io"))) }
}

val coroutinesModule = module {
    factory(named("io")) { Dispatchers.IO }
    factory(named("main")) { Dispatchers.Main }
    factory(named("default")) { Dispatchers.Default }
}

val databaseModule = module {
    single<SessionDatabase> {
        val db =
            Room.databaseBuilder(androidApplication(), SessionDatabase::class.java, "session_db")
                .build()
        db
    }
    single { SessionHelper(get<SessionDatabase>().sessionDao()) }
}

val Dispatchers.io: CoroutineDispatcher
    get() = getKoin().get(named("io"))

val Dispatchers.main: MainCoroutineDispatcher
    get() = getKoin().get(named("main"))

val Dispatchers.default: CoroutineDispatcher
    get() = getKoin().get(named("default"))

val sessionHelper: SessionHelper
    get() = getKoin().get<SessionHelper>()