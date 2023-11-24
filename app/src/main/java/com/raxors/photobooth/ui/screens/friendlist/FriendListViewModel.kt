package com.raxors.photobooth.ui.screens.friendlist

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.raxors.photobooth.core.base.BaseViewModel
import com.raxors.photobooth.domain.AppRepository
import com.raxors.photobooth.domain.models.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

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

    private val _searchResult = MutableStateFlow<PagingData<User>>(PagingData.empty())
    val searchResult: StateFlow<PagingData<User>> = _searchResult.asStateFlow()

    init {
        onInit()
    }

    private fun onInit() {
        fetchData()
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

            is FriendListUiEvent.OnSearchTextChanged -> {
                setState { copy(searchText = event.query) }
                if (event.query.length > 2)
                    searchUser(event.query)
                else
                    _searchResult.value = PagingData.empty()
            }

            FriendListUiEvent.OnToggleSearch -> {
                setState { copy(isSearching = !isSearching, searchText = if (isSearching) "" else searchText) }
            }

            FriendListUiEvent.OnClearSearch -> {
                setState { copy(isSearching = false, searchText = "",) }
                _searchResult.value = PagingData.empty()
            }
        }
    }

    @OptIn(FlowPreview::class)
    private fun searchUser(query: String) {
        launch({
            repo.searchUser(query).flow.cachedIn(viewModelScope).collectLatest { data ->
                _searchResult.value = data
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
            repo.getOutgoingRequests().flow.cachedIn(viewModelScope).collectLatest { data ->
//                setState { copy(outgoingList = data) }
                _outgoingRequests.value = data
                setState { copy(isLoading = false) }
            }
        }, onError = {
            it.printStackTrace()
        })
    }

    fun fetchData() {
        loadIncoming()
        loadOutgoing()
        loadFriends()
    }


}