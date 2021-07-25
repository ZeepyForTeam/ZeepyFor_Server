package com.zeepy.server.email.util;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

/**
 * Created by Minky on 2021-07-25
 */

@Component
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class EmailSendUtility {
    private final JavaMailSender javaMailSender;
    private final String fromEmail = "no_reply@zeepy.com";

    public void mailSend(
            String email,
            String title,
            String description
    ) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setFrom(fromEmail);
        message.setSubject(title);
        message.setText(description);

        javaMailSender.send(message);
    }
}
