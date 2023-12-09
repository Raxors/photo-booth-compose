package com.raxors.photobooth.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.raxors.photobooth.data.api.PhotoBoothApi
import com.raxors.photobooth.data.mappers.toImage
import com.raxors.photobooth.data.mappers.toUser
import com.raxors.photobooth.data.models.request.AddUserRequest
import com.raxors.photobooth.data.models.request.ChangeAvatarRequest
import com.raxors.photobooth.data.models.request.ChangeProfileRequest
import com.raxors.photobooth.data.models.request.DeleteUserRequest
import com.raxors.photobooth.data.models.request.SendPhotoRequest
import com.raxors.photobooth.data.pagingsource.FriendPagingSource
import com.raxors.photobooth.data.pagingsource.IncomingRequestPagingSource
import com.raxors.photobooth.data.pagingsource.OutgoingRequestPagingSource
import com.raxors.photobooth.data.pagingsource.SearchPagingSource
import com.raxors.photobooth.domain.AppRepository
import com.raxors.photobooth.domain.models.Image
import com.raxors.photobooth.domain.models.User

class AppRepositoryImpl(
    private val api: PhotoBoothApi,
    private val dataStore: DataStore<Preferences>,
) : AppRepository {

    override suspend fun getFriendList(): Pager<Int, User> =
        Pager(
            config = PagingConfig(pageSize = 30, enablePlaceholders = false, initialLoadSize = 30),
            pagingSourceFactory = { FriendPagingSource(api) }
        )

    override suspend fun getOutgoingRequests(): Pager<Int, User> =
        Pager(
            config = PagingConfig(pageSize = 30, enablePlaceholders = false, initialLoadSize = 30),
            pagingSourceFactory = { OutgoingRequestPagingSource(api) }
        )

    override suspend fun getIncomingRequests(): Pager<Int, User> =
        Pager(
            config = PagingConfig(pageSize = 30, enablePlaceholders = false, initialLoadSize = 30),
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

    override suspend fun getUser(userId: String): User =
        api.getUser(userId).toUser()

    override suspend fun sendPhoto(listId: List<String>?, base64: String) =
        api.sendPhoto(SendPhotoRequest(base64, listId))

    override suspend fun getAllImages(): List<Image> =
        api.getAllPhotos().map { it.toImage() }

    override suspend fun getProfile(): User =
        api.getProfile().toUser()

    override suspend fun changeProfile(name: String?, status: String?) =
        api.changeProfile(ChangeProfileRequest(name, status))

    override suspend fun changeAvatar(file: String) =
        api.changeAvatar(ChangeAvatarRequest(file))

    override suspend fun getImageInfo(imageId: String): Image =
        api.getImageInfo(imageId).toImage()

}