package com.raxors.photobooth.ui.screens.friendlist

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.raxors.photobooth.core.base.BaseViewModel
import com.raxors.photobooth.domain.AppRepository
import com.raxors.photobooth.domain.models.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@HiltViewModel
class FriendListViewModel @Inject constructor(
    private val repo: AppRepository
) : BaseViewModel<FriendListUiState, FriendListUiEvent>() {

    private val _incomingRequests = MutableStateFlow<PagingData<User>>(PagingData.empty())
    val incomingRequests: StateFlow<PagingData<User>> = _incomingRequests.asStateFlow()

    private val _outgoingRequests = MutableStateFlow<PagingData<User>>(PagingData.empty())
    val outgoingRequests: StateFlow<PagingData<User>> = _outgoingRequests.asStateFlow()

    private val _friends = MutableStateFlow<PagingData<User>>(PagingData.empty())
    val friends: StateFlow<PagingData<User>> = _friends.asStateFlow()

    private val _search = MutableStateFlow<PagingData<User>>(PagingData.empty())
    val search: StateFlow<PagingData<User>> = _search.asStateFlow()

    init {
        onInit()
    }

    private fun onInit() {
        loadIncoming()
        loadOutgoing()
        loadFriends()
    }

    fun onEvent(event: FriendListUiEvent) {
        when (event) {
            is FriendListUiEvent.OnAddUser -> {
                addUser(event.value)
            }

            is FriendListUiEvent.OnDeleteUser -> {
                deleteUser(event.value)
            }
            FriendListUiEvent.RefreshAll -> {
                onInit()
            }
            FriendListUiEvent.IncomingExpand -> {
                setState { copy(isIncomingExpanded = !isIncomingExpanded) }
            }
            FriendListUiEvent.OutgoingExpand -> {
                setState { copy(isOutgoingExpanded = !isOutgoingExpanded) }
            }
            FriendListUiEvent.FriendsExpand -> {
                setState { copy(isFriendsExpanded = !isFriendsExpanded) }
            }
            is FriendListUiEvent.OnSearch -> {
                searchUser(event.query)
            }
        }
    }

    private fun searchUser(query: String) {
        launch({
            repo.searchUser(query).flow.cachedIn(viewModelScope).collectLatest { data ->
                _search.value = data
            }
        }, onError = {
            //TODO handle error
        })
    }

    private fun addUser(user: User) {
        launch({
            repo.addUser(user.id)
            onEvent(FriendListUiEvent.RefreshAll)
        }, onError = {
            //TODO handle exceptions
        })
    }

    private fun deleteUser(user: User) {
        launch({
            repo.deleteUser(user.id)
            onEvent(FriendListUiEvent.RefreshAll)
        }, onError = {
            //TODO handle exceptions
        })
    }

    override fun initialState(): FriendListUiState = FriendListUiState(isLoading = true)

    private fun loadFriends() {
        launch({
            repo.getFriendList().flow.cachedIn(viewModelScope).collectLatest { data ->
//               setState { copy(friendList = data) }
                _friends.value = data
            }
        }, onError = {
            it.printStackTrace()
        })
    }

    private fun loadIncoming() {
        launch({
            repo.getIncomingRequests().flow.cachedIn(viewModelScope).collectLatest { data ->
//                setState { copy(incomingList = data) }
                _incomingRequests.value = data
            }
        }, onError = {
            it.printStackTrace()
        })
    }

    private fun loadOutgoing() {
        launch({
            repo.getFriendList().flow.cachedIn(viewModelScope).collectLatest { data ->
//                setState { copy(outgoingList = data) }
                _outgoingRequests.value = data
            }
        }, onError = {
            it.printStackTrace()
        })
    }


}