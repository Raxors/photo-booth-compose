package com.raxors.photobooth.core.messaging

import android.R.id.message
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import coil.ImageLoader
import coil.request.ImageRequest
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.raxors.photobooth.R
import com.raxors.photobooth.domain.AppRepository
import com.raxors.photobooth.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class FirebaseMessaging: FirebaseMessagingService() {

    @Inject
    lateinit var repo: AppRepository

    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    override fun onMessageReceived(message: RemoteMessage) {
//        super.onMessageReceived(message)

        val notification = message.notification
        if (notification != null) {
            val data = message.data
            val imageUrl = data["image"]
            val imageId = data["image_id"]
            val clickAction = notification.clickAction
            showNotification(
                notification.title,
                notification.body,
                imageUrl,
                imageId,
                clickAction
            )
        }
    }

    private fun showNotification(title: String?, body: String?, imageUrl: String?, imageId: String?, clickAction: String?) {
        // Pass the intent to switch to the MainActivity
        val intent = Intent(clickAction)
        intent.putExtra("image_id", imageId)
        val channelId = getString(R.string.default_notification_channel_id)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )

        var bitmap: Bitmap? = null
        val loader = ImageLoader(this)
        if (!imageUrl.isNullOrBlank()) {
            val req = ImageRequest.Builder(this)
                .data(imageUrl) // demo link
                .target { result ->
                    bitmap = (result as BitmapDrawable).bitmap
                    val builder: NotificationCompat.Builder =
                        NotificationCompat.Builder(applicationContext, channelId)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentTitle(title)
                            .setContentText(body)
                            .setAutoCancel(true)
                            .setVibrate(
                                longArrayOf(
                                    1000, 1000, 1000,
                                    1000, 1000
                                )
                            )
                            .setLargeIcon(bitmap)
                            .setStyle(
                                NotificationCompat.BigPictureStyle()
                                    .bigPicture(bitmap)
                                    .bigLargeIcon(bitmap)
                            )
                            .setOnlyAlertOnce(true)
                            .setContentIntent(pendingIntent)


                    val notificationManager = getSystemService(
                        NOTIFICATION_SERVICE
                    ) as NotificationManager
                    val notificationChannel = NotificationChannel(
                        channelId, "notification_channel",
                        NotificationManager.IMPORTANCE_HIGH
                    )
                    notificationManager.createNotificationChannel(
                        notificationChannel
                    )
                    notificationManager.notify(0, builder.build())
                }
                .build()

            val disposable = loader.enqueue(req)
        } else {

            val builder: NotificationCompat.Builder =
                NotificationCompat.Builder(applicationContext, channelId)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(title)
                    .setContentText(body)
                    .setAutoCancel(true)
                    .setVibrate(
                        longArrayOf(
                            1000, 1000, 1000,
                            1000, 1000
                        )
                    )
                    .setLargeIcon(bitmap)
                    .setStyle(
                        NotificationCompat.BigPictureStyle()
                            .bigPicture(bitmap)
                            .bigLargeIcon(bitmap)
                    )
                    .setOnlyAlertOnce(true)
                    .setContentIntent(pendingIntent)


            val notificationManager = getSystemService(
                NOTIFICATION_SERVICE
            ) as NotificationManager
            val notificationChannel = NotificationChannel(
                channelId, "notification_channel",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(
                notificationChannel
            )
            notificationManager.notify(0, builder.build())
        }
    }

    override fun onNewToken(token: String) {
        Log.d("PHOTO_BOOTH_FIREBASE_TOKEN", "Refreshed token: $token")
        scope.launch {
            try {
                repo.sendFcmToken(token)
            } catch (e: Exception) {
                //TODO handle errors
            }
        }
    }

}