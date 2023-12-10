package com.raxors.photobooth.core.messaging

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.raxors.photobooth.domain.AppRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@AndroidEntryPoint
class FirebaseMessaging: FirebaseMessagingService() {

    @Inject
    lateinit var repo: AppRepository

    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
    }

    override fun onNewToken(token: String) {
        Log.d("PHOTO_BOOTH_FIREBASE_TOKEN", "Refreshed token: $token")
        scope.launch {
            try {
                repo.saveFcmToken(token)
                repo.sendFcmToken(token)
            } catch (e: Exception) {
                //TODO handle errors
            }
        }
    }

}