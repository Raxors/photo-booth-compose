package com.raxors.photobooth.ui.screens.friendlist

import androidx.paging.PagingData
import com.raxors.photobooth.core.UiState
import com.raxors.photobooth.domain.models.User
import kotlinx.coroutines.flow.MutableStateFlow

data class FriendListUiState(
    val isLoading: Boolean = true,

    val incomingList: PagingData<User> = PagingData.empty(),
    val outgoingList: PagingData<User> = PagingData.empty(),
    val friendList: PagingData<User> = PagingData.empty(),
    val searchList: PagingData<User> = PagingData.empty(),

    val isIncomingExpanded: Boolean = false,
    val isOutgoingExpanded: Boolean = false,
    val isFriendsExpanded: Boolean = true,

    val searchText: String = "",
    val isSearching: Boolean = false
): UiState