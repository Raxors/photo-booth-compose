package com.raxors.photobooth.ui.screens.camera.bottomsheet

import android.graphics.Bitmap
import android.util.Base64
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.raxors.photobooth.core.base.BaseViewModel
import com.raxors.photobooth.core.utils.Extensions.encodeImage
import com.raxors.photobooth.domain.AppRepository
import com.raxors.photobooth.domain.models.User
import com.raxors.photobooth.ui.screens.camera.CameraUiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import java.io.ByteArrayOutputStream
import javax.inject.Inject

@HiltViewModel
class SendImageViewModel @Inject constructor(
    private val repo: AppRepository,
) : BaseViewModel<SendImageUiState, SendImageUiEvent>() {

    override fun initialState(): SendImageUiState = SendImageUiState()

    init {
        getFriends()
    }

    fun onEvent(event: SendImageUiEvent) {
        when (event) {
            is SendImageUiEvent.CheckAll -> {
                setState {
                    copy(
                        isAllChecked = !isAllChecked,
                        selectedFriends = if (!isAllChecked) setOf() else selectedFriends
                    )
                }
            }

            is SendImageUiEvent.CheckUser -> {
                setState {
                    copy(
                        isAllChecked = false,
                        selectedFriends = if (selectedFriends.contains(event.user))
                            selectedFriends.toMutableSet().apply { remove(event.user) }
                        else
                            selectedFriends.toMutableSet().apply { add(event.user) }
                    )
                }
            }

            is SendImageUiEvent.CloseBottomSheet -> {
                setState { initialState() }
            }

            is SendImageUiEvent.SendPhoto -> {
                val listIds = if (state.value.isAllChecked) null
                else state.value.selectedFriends.map { it.id }
                val encodedImage = event.bitmap.encodeImage()
                sendImageToFriends(encodedImage, listIds)
            }
        }
    }

    private fun getFriends() {
        launch({
            repo.getFriendList().flow.cachedIn(viewModelScope).collectLatest { data ->
                setState { copy(friendList = data) }
            }
        }, onError = {
            //TODO handle errors
        })
    }

    fun getFriendListStateFlow(): StateFlow<PagingData<User>> =
        MutableStateFlow(state.value.friendList)

    private fun sendImageToFriends(base64Image: String, listIds: List<String>?) {
        launch({
            repo.sendPhoto(listIds, base64Image)
            setState { copy(isPhotoSent = true) }
        }, onError = {
            //TODO handle errors
        })
    }
}