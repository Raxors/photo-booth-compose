package com.raxors.photobooth.ui.screens.history_detail

import androidx.lifecycle.SavedStateHandle
import com.raxors.photobooth.core.base.BaseViewModel
import com.raxors.photobooth.domain.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HistoryDetailViewModel @Inject constructor(
    private val repo: AppRepository,
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel<HistoryDetailUiState, HistoryDetailUiEvent>() {

    override fun initialState(): HistoryDetailUiState = HistoryDetailUiState(isLoading = true)

    private val imageId = checkNotNull(savedStateHandle.get<String>("imageId"))

    init {
        getImage()
    }

    fun onEvent(event: HistoryDetailUiEvent) {

    }

    private fun getImage() {
        launch({
            val image = repo.getImageInfo(imageId)
            val user = image.ownerId?.let { repo.getUser(it) }
            setState { copy(isLoading = false, image = image, user = user) }
        }, onError = {
            //TODO handle errors
        })
    }

}