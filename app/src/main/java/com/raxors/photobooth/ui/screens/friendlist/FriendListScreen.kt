package com.raxors.photobooth.ui.screens.friendlist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.raxors.photobooth.core.navigation.CommonScreen
import com.raxors.photobooth.domain.models.User
import com.raxors.photobooth.ui.screens.friendlist.components.CommonList
import com.raxors.photobooth.ui.screens.friendlist.components.ExpandableList
import com.raxors.photobooth.ui.screens.friendlist.components.ExpandableListHeader
import com.raxors.photobooth.ui.screens.friendlist.components.FriendItem
import com.raxors.photobooth.ui.screens.friendlist.components.IncomingItem
import com.raxors.photobooth.ui.screens.friendlist.components.OutgoingItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FriendListScreen(
    navHostController: NavHostController,
    viewModel: FriendListViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    val incoming = viewModel.incomingRequests.collectAsLazyPagingItems()
    val outgoing = viewModel.outgoingRequests.collectAsLazyPagingItems()
    val friends = viewModel.friends.collectAsLazyPagingItems()
    val searchResult = viewModel.searchResult.collectAsLazyPagingItems()
    val refreshState = rememberSwipeRefreshState(isRefreshing = state.isLoading)

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.secondaryContainer).padding(bottom = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                SearchBar(
                    query = state.searchText,
                    active = state.isSearching,
                    onActiveChange = { viewModel.onEvent(FriendListUiEvent.OnToggleSearch) },
                    onQueryChange = {
                        viewModel.onEvent(FriendListUiEvent.OnSearchTextChanged(it))
                    },
                    onSearch = {
                        viewModel.onEvent(FriendListUiEvent.OnSearchTextChanged(it))
                    },
                    placeholder = { Text("Type username") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    trailingIcon = {
                        if (state.searchText.isNotBlank()) {
                            Icon(
                                Icons.Default.Close,
                                contentDescription = null,
                                modifier = Modifier.clickable {
                                    viewModel.onEvent(FriendListUiEvent.OnClearSearch)
                                })
                        }
                    }
                ) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(count = searchResult.itemCount, key = searchResult.itemKey { it.id }) {
                            val item = searchResult[it]
                            item?.let { user ->
                                FriendItem(
                                    item = user,
                                    openUser = {
                                        navHostController.navigate(CommonScreen.User(user.id).route)
                                    })
                            }
                        }
                    }
                }
            }
        }
    ) {
            SwipeRefresh(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                state = refreshState,
                onRefresh = viewModel::fetchData
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    if (friends.loadState.refresh is LoadState.Loading) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center)
                        )
                    } else {
                        Column {
                            if (incoming.itemCount > 0) {
                                ExpandableListHeader(
                                    title = "Incoming Requests",
                                    isExpanded = state.isIncomingExpanded,
                                    clickExpand = { viewModel.onEvent(FriendListUiEvent.IncomingExpand) }
                                ) { isExpanded ->
                                    ExpandableList(
                                        items = incoming,
                                        isExpanded = isExpanded
                                    ) { item ->
                                        IncomingItem(
                                            item = item,
                                            accept = {
                                                viewModel.onEvent(FriendListUiEvent.OnAddUser(item))
                                            },
                                            decline = {
                                                viewModel.onEvent(
                                                    FriendListUiEvent.OnDeleteUser(
                                                        item
                                                    )
                                                )
                                            },
                                        )
                                    }
                                }
                            }
                            if (outgoing.itemCount > 0) {
                                ExpandableListHeader(
                                    title = "Outgoing Requests",
                                    isExpanded = state.isOutgoingExpanded,
                                    clickExpand = { viewModel.onEvent(FriendListUiEvent.OutgoingExpand) }
                                ) { isExpanded ->
                                    ExpandableList(
                                        items = outgoing,
                                        isExpanded = isExpanded
                                    ) { item ->
                                        OutgoingItem(
                                            item = item,
                                            decline = {
                                                viewModel.onEvent(
                                                    FriendListUiEvent.OnDeleteUser(
                                                        item
                                                    )
                                                )
                                            }
                                        )
                                    }
                                }
                            }
                            ExpandableListHeader(
                                title = "Friends",
                                isExpanded = state.isFriendsExpanded,
                                clickExpand = { viewModel.onEvent(FriendListUiEvent.FriendsExpand) },
                                isRoundedBottom = true
                            ) { isExpanded ->
                                ExpandableList(items = friends, isExpanded = isExpanded) { item ->
                                    FriendItem(
                                        item = item,
                                        openUser = {
                                            navHostController.navigate(CommonScreen.User(item.id).route)
                                        })
                                }
                            }
                        }
                    }
                }
            }
        }
}