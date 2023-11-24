package com.raxors.photobooth.ui.screens.friendlist

import androidx.paging.PagingData
import com.raxors.photobooth.core.UiState
import com.raxors.photobooth.domain.models.User
import kotlinx.coroutines.flow.MutableStateFlow

data class FriendListUiState(
    val isLoading: Boolean = true,
    val isIncomingExpanded: Boolean = false,
    val isOutgoingExpanded: Boolean = false,
    val isFriendsExpanded: Boolean = true,

    val searchText: String = "",
    val isSearching: Boolean = false
): UiState