package com.example.testcomposeapp.ui.login

import androidx.lifecycle.*
import com.example.testcomposeapp.R
import com.example.testcomposeapp.data.source.local.SessionHelper
import com.example.testcomposeapp.domain.*
import com.vishnu.testapplication.data.LoginRequest
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class LaunchViewModel(
    private val onBoardingCompletedUseCase: OnBoardingCompletedUseCase,
    private val onBoardingCompleteUseCase: OnBoardingCompleteUseCase,
    private val loginUseCase: LoginUseCase,
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    val userName = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    private val _isCustomerOnboarded: Flow<Boolean> = flow {
        val isOnboarded = onBoardingCompletedUseCase()
        emit(isOnboarded)
    }

    val isCustomerOnboarded: LiveData<Boolean> = _isCustomerOnboarded.asLiveData()

    private val _launchApplication: Flow<Boolean> = flow {
        delay(2000)
        emit(false)
    }

    val launchApplication: LiveData<Boolean> = _launchApplication.asLiveData()

    val isLoginButtonEnable = MediatorLiveData<Boolean>().apply {
        fun validUserName() = userName.value.orEmpty().length > 3
        fun validPassword() = password.value.orEmpty().length > 3

        fun validate() = validUserName() && validPassword()
        addSource(userName) { this.value = validate() }
        addSource(password) { this.value = validate() }
    }

    /**
     * Calls Login asynchronously with CoroutineLiveData (Livedata inbuild with CoroutineContext & CoroutineScope)
     * the following is same as, updating a liveData with dispatcher.io and viewmodelScope.
     */
    private val loginEvent = MutableLiveData<Unit>()
    val login = loginEvent.switchMap {
        liveData(ioDispatcher) {
            emit(Result.Loading)
            val loginRequest = LoginRequest(
                username = userName.value.orEmpty(),
                password = password.value.orEmpty()
            )
            val response = loginUseCase(loginRequest)
            emit(response)
        }
    }.map { Event(it) } // map to Single Event for one time EventObserver

    /**
     * Performs Launch time initialization or DeepLink parsing.
     */
    fun login() = viewModelScope.launch {
        loginEvent.value = Unit
    }

    /**
     * Performs Launch time initialization or DeepLink parsing.
     */
    fun setUserOnboarded() = viewModelScope.launch {
        onBoardingCompleteUseCase(true)
    }

    val drawableResId: LiveData<List<Int>> =
        MutableLiveData<List<Int>>(
            arrayListOf(
                R.drawable.welcome1,
                R.drawable.welcome2,
                R.drawable.welcome3,
                R.drawable.welcome4,
                R.drawable.welcome5,
            )
        )

}