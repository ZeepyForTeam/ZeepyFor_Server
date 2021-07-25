package com.zeepy.server.email.service;

import com.zeepy.server.email.domain.Email;
import com.zeepy.server.email.dto.EmailRequestDto;
import com.zeepy.server.email.repository.EmailRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Minky on 2021-07-25
 */

@Service
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class EmailService {
    private final EmailRepository emailRepository;

    @Transactional
    public Long create(EmailRequestDto emailRequestDto) {
        Email email = emailRequestDto.returnEmailEntity();
        Email save = emailRepository.save(email);
        return save.getId();
    }
}
