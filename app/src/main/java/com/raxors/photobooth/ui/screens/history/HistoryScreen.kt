package com.raxors.photobooth.ui.screens.history

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import coil.compose.AsyncImage
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.raxors.photobooth.BuildConfig
import com.raxors.photobooth.core.navigation.CommonScreen

@Composable
fun HistoryScreen(
    navHostController: NavHostController,
    viewModel: HistoryViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()
    val images = viewModel.images.collectAsLazyPagingItems()
    val refreshState = rememberSwipeRefreshState(isRefreshing = state.isLoading)
    SwipeRefresh(
        state = refreshState,
        onRefresh = viewModel::getHistory
    ) {
        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize(),
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(
                count = images.itemCount,
                key = images.itemKey { item -> item.id }
            ) { index ->
                index.let {
                    val item = images[it]
                    item?.let { image ->
                        AsyncImage(
                            modifier = Modifier
                                .clip(RoundedCornerShape(16.dp))
                                .clickable {
                                    navHostController.navigate(CommonScreen.HistoryDetail(image.id).route)
                                },
                            model = BuildConfig.BASE_HOST + image.path,
                            contentDescription = image.id
                        )
                    }
                }
            }
        }
    }
}