package com.zeepy.server.push.controller;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.zeepy.server.push.dto.PushManyTargetRequestDto;
import com.zeepy.server.push.service.PushService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Created by Minky on 2021-07-22
 */

@RestController
@RequestMapping("/api/push")
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class PushController {
    private final PushService pushService;

    @PostMapping("/all")
    public ResponseEntity<Void> notifyAllUsers(
            @Valid @RequestBody PushManyTargetRequestDto pushManyTargetRequestDto
    ) throws FirebaseMessagingException {
        pushService.pushByAllUsers(pushManyTargetRequestDto);
        return ResponseEntity.ok().build();
    }
}
