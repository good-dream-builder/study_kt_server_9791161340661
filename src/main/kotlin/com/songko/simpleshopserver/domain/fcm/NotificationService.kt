package com.songko.simpleshopserver.domain.fcm


import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import com.songko.simpleshopserver.domain.user.User
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service

/**
 * 푸시 메시지를 보낼 수 있다.
 * ex) NotificationService.sendToUser(user, title, content)
 */
@Service
class NotificationService {
    // 1
    private val firebaseApp by lazy {
        val inputStream = ClassPathResource(
                "simple-shop-aos-firebase-adminsdk-bfn0x-76d4fe19bf.json" // 2
        ).inputStream

        val options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(inputStream))
                .build()

        FirebaseApp.initializeApp(options)  // 3
    }

    fun sendToUser(user: User, title: String, content: String) =
            user.fcmToken?.let { token ->
                // 4
                val message = Message.builder()
                        .setToken(token)
                        .putData("title", title)
                        .putData("content", content)
                        .build()
                FirebaseMessaging.getInstance(firebaseApp).send(message) // 5
            }


}