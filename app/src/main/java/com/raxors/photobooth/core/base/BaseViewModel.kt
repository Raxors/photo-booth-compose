package com.raxors.photobooth.core.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raxors.photobooth.core.UiEvent
import com.raxors.photobooth.core.UiState
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.shareIn


typealias Block<T> = suspend () -> T
typealias Error = suspend (e: Exception) -> Unit
typealias Cancel = suspend (e: Exception) -> Unit

abstract class BaseViewModel<STATE : UiState, EVENT : UiEvent> : ViewModel() {

    private val _state = MutableStateFlow(initialState())
    val state: StateFlow<STATE>
        get() = _state.asStateFlow()

    private val _uiEvent = MutableSharedFlow<EVENT>()
    val uiEvent = _uiEvent.asSharedFlow()
        .shareIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
        )

    protected abstract fun initialState(): STATE

    protected fun setState(reducer: STATE.() -> STATE) {
        val currentState = _state.value
        _state.value = reducer(currentState)
    }

//    protected fun onEvent(event: EVENT) {
//        _uiEvent
//    }

    protected fun launch(block: Block<Unit>, onError: Error? = null, onCancel: Cancel? = null): Job {
        return viewModelScope.launch(Dispatchers.IO) {
            try {
                block.invoke()
            } catch (e: Exception) {
                when (e) {
                    is CancellationException -> {
                        onCancel?.invoke(e)
                    }

                    else -> {
                        onError?.invoke(e)
                    }
                }
            }
        }
    }

    protected fun <T> async(block: Block<T>): Deferred<T> {
        return viewModelScope.async { block.invoke() }
    }

}