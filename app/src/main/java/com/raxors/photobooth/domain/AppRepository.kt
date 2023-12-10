package com.raxors.photobooth.domain

import androidx.paging.Pager
import com.raxors.photobooth.domain.models.Image
import com.raxors.photobooth.domain.models.User
import kotlinx.coroutines.flow.Flow

interface AppRepository {

    suspend fun getFriendList(): Pager<Int, User>
    suspend fun getOutgoingRequests(): Pager<Int, User>
    suspend fun getIncomingRequests(): Pager<Int, User>
    suspend fun deleteUser(userId: String)
    suspend fun addUser(userId: String)
    suspend fun searchUser(username: String): Pager<Int, User>
    suspend fun getUser(userId: String): User
    suspend fun sendPhoto(listId: List<String>?, base64: String)
    suspend fun getAllImages(): List<Image>
    suspend fun getProfile(): User
    suspend fun changeProfile(name: String? = null, status: String? = null)
    suspend fun changeAvatar(file: String)
    suspend fun getImageInfo(imageId: String): Image
    suspend fun sendFcmToken(fcmToken: String)
    suspend fun saveFcmToken(fcmToken: String)
    fun getFcmToken(): Flow<String>

}