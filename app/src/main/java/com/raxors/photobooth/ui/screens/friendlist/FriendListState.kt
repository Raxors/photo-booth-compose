package com.raxors.photobooth.ui.screens.friendlist

import com.raxors.photobooth.core.ScreenState
import com.raxors.photobooth.domain.models.User

data class FriendListState(
    val friendList: List<User> = mutableListOf(),
    val friendOutgoingList: List<User> = mutableListOf(),
    val friendIncomingList: List<User> = mutableListOf(),
    val isLoading: Boolean = false
): ScreenState