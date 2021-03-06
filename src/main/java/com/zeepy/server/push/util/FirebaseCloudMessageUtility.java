package com.zeepy.server.push.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.zeepy.server.common.CustomExceptionHandler.CustomException.FirebaseCloudMessageException;

/**
 * Created by Minky on 2021-07-22
 */

@Component
public class FirebaseCloudMessageUtility {
    private final String firebaseAdminServiceAccountPath = "src/main/resources/config/zeepy-7b1f7-firebase-adminsdk-g5eng-1df0561a25.json";
    private FirebaseMessaging instance;

    @PostConstruct // 실행 전에 먼저 해당 함수를 실행 시키는 어노테이션
    private void initFirebaseOptions() throws IOException {
        // TODO: new ClassPathResource("google-fcm-...-key.json").getInputStream() -> 배포시 해당 포맷으로 변경
        GoogleCredentials googleCredentials = GoogleCredentials
            .fromStream(new FileInputStream(firebaseAdminServiceAccountPath)) // Firebase Admin Key Path
            .createScoped(Arrays.asList("https://www.googleapis.com/auth/cloud-platform")); // Firebase 권한

        FirebaseApp firebaseApp = null;
        List<FirebaseApp> firebaseApps = FirebaseApp.getApps();
        if (firebaseApps != null && !firebaseApps.isEmpty()) {
            for (FirebaseApp app : firebaseApps) {
                if (app.getName().equals(FirebaseApp.DEFAULT_APP_NAME)) {
                    firebaseApp = app;
                }
            }
        } else {
            FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(googleCredentials)
                .build();

            firebaseApp = FirebaseApp.initializeApp(options);
        }

        this.instance = FirebaseMessaging.getInstance(firebaseApp);
    }

    public void sendTargetMessage(
        String targetToken,
        String title,
        String body
    ) {
        Message message = makeTargetMessage(targetToken, title, body);

        try {
            this.instance.send(message);
        } catch (FirebaseMessagingException e) {
            throw new FirebaseCloudMessageException();
        }
    }

    public void sendTopicMessage(
        String topic,
        String title,
        String body
    ) {
        Message message = makeTopicMessage(topic, title, body);
        try {
            this.instance.send(message);
        } catch (FirebaseMessagingException e) {
            throw new FirebaseCloudMessageException();
        }
    }

    private Message makeTargetMessage(
        String targetToken,
        String title,
        String body
    ) {
        Notification notification = new Notification(title, body);

        return Message.builder()
            .setToken(targetToken)
            .setNotification(notification)
            .build();

    }

    private Message makeTopicMessage(
        String topic,
        String title,
        String body
    ) {
        Notification notification = new Notification(title, body);

        return Message.builder()
            .setTopic(topic)
            .setNotification(notification)
            .build();
    }
}
