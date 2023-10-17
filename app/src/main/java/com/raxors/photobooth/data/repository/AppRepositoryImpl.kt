package com.raxors.photobooth.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.raxors.photobooth.data.api.PhotoBoothApi
import com.raxors.photobooth.data.models.request.AddUserRequest
import com.raxors.photobooth.data.models.request.DeleteUserRequest
import com.raxors.photobooth.data.pagingsource.FriendPagingSource
import com.raxors.photobooth.data.pagingsource.IncomingRequestPagingSource
import com.raxors.photobooth.data.pagingsource.OutgoingRequestPagingSource
import com.raxors.photobooth.data.pagingsource.SearchPagingSource
import com.raxors.photobooth.domain.AppRepository
import com.raxors.photobooth.domain.models.User

class AppRepositoryImpl(
    private val api: PhotoBoothApi,
    private val dataStore: DataStore<Preferences>
) : AppRepository {

    override suspend fun getFriendList(): Pager<Int, User> =
        Pager(
            config = PagingConfig(pageSize = 10, enablePlaceholders = false),
            pagingSourceFactory = { FriendPagingSource(api) }
        )

    override suspend fun getOutgoingRequests(): Pager<Int, User> =
        Pager(
            config = PagingConfig(pageSize = 10, enablePlaceholders = false),
            pagingSourceFactory = { OutgoingRequestPagingSource(api) }
        )

    override suspend fun getIncomingRequests(): Pager<Int, User> =
        Pager(
            config = PagingConfig(pageSize = 10, enablePlaceholders = false),
            pagingSourceFactory = { IncomingRequestPagingSource(api) }
        )

    override suspend fun deleteUser(userId: String) =
        api.deleteUser(DeleteUserRequest(userId))

    override suspend fun addUser(userId: String) =
        api.addUser(AddUserRequest(userId))

    override suspend fun searchUser(username: String): Pager<Int, User> =
        Pager(
            config = PagingConfig(pageSize = 10, enablePlaceholders = false),
            pagingSourceFactory = { SearchPagingSource(api, username) }
        )

}