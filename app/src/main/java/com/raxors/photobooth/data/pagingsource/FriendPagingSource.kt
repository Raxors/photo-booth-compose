package com.raxors.photobooth.data.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.raxors.photobooth.data.api.PhotoBoothApi
import com.raxors.photobooth.data.mappers.toUser
import com.raxors.photobooth.domain.models.User

class FriendPagingSource(
    private val api: PhotoBoothApi,
) : PagingSource<Int, User>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        val page = params.key ?: 0
        return try {
            val friends = api.getFriends(page, params.loadSize).map { it.toUser() }
            LoadResult.Page(
                data = friends,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (friends.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        return state.anchorPosition
    }
}
