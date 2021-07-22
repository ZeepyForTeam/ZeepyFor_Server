package com.zeepy.server.push.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by Minky on 2021-07-21
 */

@Service
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class PushService {
    private final String firebaseAdminServiceAccountPath = "src/main/resources/key/zeepy-7b1f7-firebase-adminsdk-g5eng-1df0561a25.json";
    private FirebaseMessaging instance;

    @PostConstruct // 실행 전에 먼저 해당 함수를 실행 시키는 어노테이션
    private void initFirebaseOptions() throws IOException {
        // TODO: new ClassPathResource("google-fcm-...-key.json").getInputStream() -> 배포시 해당 포맷으로 변경
        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new FileInputStream(firebaseAdminServiceAccountPath)) // Firebase Admin Key Path
                .createScoped(Arrays.asList("https://www.googleapis.com/auth/cloud-platform")); // Firebase 권한

        FirebaseOptions secondaryAppConfig = FirebaseOptions.builder()
                .setCredentials(googleCredentials)
                .build();

        FirebaseApp app = FirebaseApp.initializeApp(secondaryAppConfig);
        this.instance = FirebaseMessaging.getInstance(app);
    }

    public void sendTargetMessage(
            String targetToken,
            String title,
            String body
    ) throws FirebaseMessagingException {
        Message message = makeTargetMessage(targetToken, title, body);

        this.instance.send(message);
    }

    public void sendTopicMessage(
            String topic,
            String title,
            String body
    ) throws FirebaseMessagingException  {
        Message message = makeTopicMessage(topic, title, body);

        this.instance.send(message);
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
