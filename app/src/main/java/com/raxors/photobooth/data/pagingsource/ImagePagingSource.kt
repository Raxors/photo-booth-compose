package com.raxors.photobooth.data.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.raxors.photobooth.data.api.PhotoBoothApi
import com.raxors.photobooth.data.mappers.toImage
import com.raxors.photobooth.data.mappers.toUser
import com.raxors.photobooth.domain.models.Image
import com.raxors.photobooth.domain.models.User

class ImagePagingSource(
    private val api: PhotoBoothApi,
) : PagingSource<Int, Image>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Image> {
        val page = params.key ?: 0
        return try {
            val images = api.getAllPhotos(page, params.loadSize).map { it.toImage() }
            LoadResult.Page(
                data = images,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (images.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Image>): Int? {
        return state.anchorPosition
    }
}
