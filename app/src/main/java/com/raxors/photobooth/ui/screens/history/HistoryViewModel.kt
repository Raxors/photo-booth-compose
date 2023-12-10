package com.raxors.photobooth.ui.screens.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.raxors.photobooth.core.base.BaseViewModel
import com.raxors.photobooth.domain.AppRepository
import com.raxors.photobooth.domain.models.Image
import com.raxors.photobooth.domain.models.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val repo: AppRepository
): BaseViewModel<HistoryUiState, HistoryUiEvent>() {

    private val _images = MutableStateFlow<PagingData<Image>>(PagingData.empty())
    val images: StateFlow<PagingData<Image>> = _images.asStateFlow()

    init {
        getHistory()
    }
    override fun initialState(): HistoryUiState = HistoryUiState()

    fun getHistory() {
        launch({
            repo.getAllImages().flow.cachedIn(viewModelScope).collectLatest { data ->
               setState { copy(isLoading = false) }
                _images.value = data
            }
        }, onError = {
            it.printStackTrace()
        })
    }

}