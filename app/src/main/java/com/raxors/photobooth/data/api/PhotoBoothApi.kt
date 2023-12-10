package com.raxors.photobooth.data.api

import com.raxors.photobooth.data.models.request.AddUserRequest
import com.raxors.photobooth.data.models.request.ChangeAvatarRequest
import com.raxors.photobooth.data.models.request.ChangeEmailRequest
import com.raxors.photobooth.data.models.request.ChangePasswordRequest
import com.raxors.photobooth.data.models.request.ChangeProfileRequest
import com.raxors.photobooth.data.models.request.ChangeUsernameRequest
import com.raxors.photobooth.data.models.request.DeleteUserRequest
import com.raxors.photobooth.data.models.request.LoginRequest
import com.raxors.photobooth.data.models.request.RegisterRequest
import com.raxors.photobooth.data.models.request.SendFcmTokenRequest
import com.raxors.photobooth.data.models.request.SendPhotoRequest
import com.raxors.photobooth.data.models.response.AuthInfoResponse
import com.raxors.photobooth.data.models.response.ImageResponse
import com.raxors.photobooth.data.models.response.TokenResponse
import com.raxors.photobooth.data.models.response.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface PhotoBoothApi {

    @POST("auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): TokenResponse

    @POST("auth/registration")
    suspend fun register(@Body registerRequest: RegisterRequest): TokenResponse

    @GET("auth/refreshToken")
    suspend fun refreshToken(@Header("Authorization") refreshToken: String): Response<TokenResponse>

    @GET("auth/info")
    suspend fun getAuthInfo(): AuthInfoResponse

    @GET("friends")
    suspend fun getFriends(@Query("page") page: Int, @Query("size") size: Int): List<UserResponse>

    @GET("friends/requests/outgoing")
    suspend fun getOutgoingRequests(@Query("page") page: Int, @Query("size") size: Int): List<UserResponse>

    @GET("friends/requests/incoming")
    suspend fun getIncomingRequests(@Query("page") page: Int, @Query("size") size: Int): List<UserResponse>

    @POST("friends/manage/delete")
    suspend fun deleteUser(@Body deleteUserRequest: DeleteUserRequest)

    @POST("friends/manage/add")
    suspend fun addUser(@Body addUserRequest: AddUserRequest)

    @GET("profile/search")
    suspend fun searchUser(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("username") username: String
    ): List<UserResponse>

    @GET("profile/{userId}")
    suspend fun getUser(@Path("userId") userId: String): UserResponse

    @GET("profile")
    suspend fun getProfile(): UserResponse

    @POST("image/photo")
    suspend fun sendPhoto(@Body sendPhotoRequest: SendPhotoRequest)

    @GET("image/all")
    suspend fun getAllPhotos(): List<ImageResponse>

    @PUT("profile")
    suspend fun changeProfile(@Body changeProfileRequest: ChangeProfileRequest)

    @POST("image/avatar")
    suspend fun changeAvatar(@Body changeAvatarRequest: ChangeAvatarRequest)

    @POST("auth/changeUsername")
    suspend fun changeUsername(@Body changeUsernameRequest: ChangeUsernameRequest)

    @POST("auth/changePassword")
    suspend fun changePassword(@Body changePasswordRequest: ChangePasswordRequest)

    @POST("auth/changeEmail")
    suspend fun changeEmail(@Body changeEmailRequest: ChangeEmailRequest)

    @GET("image/{imageId}/info")
    suspend fun getImageInfo(@Path("imageId") imageId: String): ImageResponse

    @POST("fcm/token")
    suspend fun sendFcmToken(@Body fcmTokenRequest: SendFcmTokenRequest)
}