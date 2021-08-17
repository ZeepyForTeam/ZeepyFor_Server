package com.zeepy.server.email.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zeepy.server.email.domain.AdminEmail;
import com.zeepy.server.email.dto.EmailRequestDto;
import com.zeepy.server.email.repository.EmailRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

/**
 * Created by Minky on 2021-07-25
 */

@Service
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class EmailService {
    private final EmailRepository emailRepository;

    @Transactional
    public Long create(EmailRequestDto emailRequestDto) {
        AdminEmail adminEmail = emailRequestDto.returnEmailEntity();
        AdminEmail save = emailRepository.save(adminEmail);
        return save.getId();
    }
}
