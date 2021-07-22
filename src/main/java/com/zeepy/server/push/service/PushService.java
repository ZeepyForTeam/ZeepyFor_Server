package com.zeepy.server.push.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.zeepy.server.push.dto.PushManyTargetRequestDto;
import com.zeepy.server.push.dto.PushOneTargetRequestDto;
import com.zeepy.server.push.util.FirebaseCloudMessageUtility;
import com.zeepy.server.user.repository.UserRepository;
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
    private final FirebaseCloudMessageUtility firebaseCloudMessageUtility;
    private final UserRepository userRepository;

    public void pushByAllUsers(
            PushManyTargetRequestDto pushManyTargetRequestDto
    ) throws FirebaseMessagingException {
        firebaseCloudMessageUtility.sendTopicMessage(
                "notify",
                pushManyTargetRequestDto.getTitle(),
                pushManyTargetRequestDto.getBody()
        );
    }

    public void pushByTargetUsersUsingTopic(
            PushOneTargetRequestDto pushOneTargetRequestDto
    ) throws FirebaseMessagingException {
        // TODO : Security Merge 후 작업
    }
}
