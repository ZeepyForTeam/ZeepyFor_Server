package com.zeepy.server.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zeepy.server.user.dto.RegistrationReqDto;
import com.zeepy.server.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
	private final UserRepository userRepository;

	@Transactional
	public void registration(RegistrationReqDto registrationReqDto) {
		userRepository.save(registrationReqDto
			.toEntity());
	}
}
