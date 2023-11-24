package com.raxors.photobooth.ui.screens.history

import androidx.lifecycle.ViewModel
import com.raxors.photobooth.core.base.BaseViewModel
import com.raxors.photobooth.domain.AppRepository
import com.raxors.photobooth.domain.models.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val repo: AppRepository
): BaseViewModel<HistoryUiState, HistoryUiEvent>() {

    init {
        getHistory()
    }
    override fun initialState(): HistoryUiState = HistoryUiState()

    fun getHistory() {
        launch({
            val images = repo.getAllImages()
            setState { copy(imageList = images, isLoading = false) }
        }, onError = {
            //TODO handle error
        })
    }

}