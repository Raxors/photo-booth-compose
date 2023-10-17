package com.raxors.photobooth.ui.screens.friendlist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
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
    val search = viewModel.search.collectAsLazyPagingItems()

    Box(modifier = Modifier.fillMaxSize()) {
        if (friends.loadState.refresh is LoadState.Loading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            var text by rememberSaveable { mutableStateOf("") }
            var active by rememberSaveable { mutableStateOf(false) }
            Column {
                SearchBar(
                    query = text,
                    active = true,
                    onActiveChange = { active = it },
                    onQueryChange = {
                        text = it
                        viewModel.onEvent(FriendListUiEvent.OnSearch(text))
                    },
                    onSearch = {
                        active = false
                        viewModel.onEvent(FriendListUiEvent.OnSearch(text))
                    },
                    placeholder = { Text("Type username") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    trailingIcon = {
                        if (text.isNotBlank()) {
                            Icon(
                                Icons.Default.Close,
                                contentDescription = null,
                                modifier = Modifier.clickable {
                                    text = ""
                                })
                        }
                    }
                ) {
                    if (text.isNotBlank()) {
                        CommonList(items = search) { item ->
                            FriendItem(item = item)
                        }
                    } else {
                        ExpandableListHeader(
                            title = "Incoming Requests",
                            itemCount = incoming.itemCount,
                            isExpanded = state.isIncomingExpanded,
                            clickExpand = { viewModel.onEvent(FriendListUiEvent.IncomingExpand) }
                        ) { isExpanded ->
                            ExpandableList(items = incoming, isExpanded = isExpanded) { item ->
                                IncomingItem(
                                    item = item,
                                    accept = { viewModel.onEvent(FriendListUiEvent.OnAddUser(item)) },
                                    decline = {
                                        viewModel.onEvent(FriendListUiEvent.OnDeleteUser(item))
                                    },
                                )
                            }
                        }
                        ExpandableListHeader(
                            title = "Outgoing Requests",
                            itemCount = outgoing.itemCount,
                            isExpanded = state.isOutgoingExpanded,
                            clickExpand = { viewModel.onEvent(FriendListUiEvent.OutgoingExpand) }
                        ) { isExpanded ->
                            ExpandableList(items = outgoing, isExpanded = isExpanded) { item ->
                                OutgoingItem(
                                    item = item,
                                    decline = {
                                        viewModel.onEvent(FriendListUiEvent.OnDeleteUser(item))
                                    }
                                )
                            }
                        }
                        ExpandableListHeader(
                            title = "Friend List",
                            itemCount = friends.itemCount,
                            isExpanded = state.isFriendsExpanded,
                            clickExpand = { viewModel.onEvent(FriendListUiEvent.FriendsExpand) }
                        ) { isExpanded ->
                            ExpandableList(items = friends, isExpanded = isExpanded) { item ->
                                FriendItem(item = item)
                            }
                        }
                    }
                }
            }
        }
    }
}