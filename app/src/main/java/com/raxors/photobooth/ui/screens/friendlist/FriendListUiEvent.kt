package com.raxors.photobooth.ui.screens.friendlist

import com.raxors.photobooth.core.UiEvent
import com.raxors.photobooth.domain.models.User

sealed interface FriendListUiEvent : UiEvent {
    data class OnDeleteUser(val value: User) : FriendListUiEvent
    data class OnAddUser(val value: User) : FriendListUiEvent
    data object RefreshAll : FriendListUiEvent
    data object IncomingExpand : FriendListUiEvent
    data object OutgoingExpand : FriendListUiEvent
    data object FriendsExpand : FriendListUiEvent
    data class OnSearch(val query: String) : FriendListUiEvent

}