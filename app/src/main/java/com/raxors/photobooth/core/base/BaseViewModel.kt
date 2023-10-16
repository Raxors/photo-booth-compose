package com.raxors.photobooth.core.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raxors.photobooth.core.ScreenEvent
import com.raxors.photobooth.core.ScreenState
import com.raxors.photobooth.ui.screens.auth.login.LoginScreenState
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


typealias Block<T> = suspend () -> T
typealias Error = suspend (e: Exception) -> Unit
typealias Cancel = suspend (e: Exception) -> Unit

abstract class BaseViewModel<STATE : ScreenState, EVENT : ScreenEvent> : ViewModel() {

    private val _state = MutableStateFlow<STATE>(initialState())
    val state: StateFlow<STATE>
        get() = _state.asStateFlow()

    protected abstract fun initialState(): STATE

    protected fun setState(reducer: STATE.() -> STATE) {
        val currentState = _state.value
        _state.value = reducer(currentState)
    }

    protected fun launch(block: Block<Unit>, error: Error? = null, cancel: Cancel? = null): Job {
        return viewModelScope.launch {
            try {
                block.invoke()
            } catch (e: Exception) {
                when (e) {
                    is CancellationException -> {
                        cancel?.invoke(e)
                    }

                    else -> {
                        error?.invoke(e)
                    }
                }
            }
        }
    }

    protected fun <T> async(block: Block<T>): Deferred<T> {
        return viewModelScope.async { block.invoke() }
    }

}