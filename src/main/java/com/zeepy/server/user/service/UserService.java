package com.zeepy.server.user.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zeepy.server.common.CustomExceptionHandler.CustomException.DuplicateEmailException;
import com.zeepy.server.common.CustomExceptionHandler.CustomException.DuplicateNicknameException;
import com.zeepy.server.user.domain.User;
import com.zeepy.server.user.dto.CheckOfRedundancyEmailReqDto;
import com.zeepy.server.user.dto.CheckOfRedundancyNicknameReqDto;
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

	@Transactional
	public void checkForRedundancyEmail(CheckOfRedundancyEmailReqDto reqDto) {
		String email = reqDto.getEmail();
		Optional<User> user = userRepository.findByEmail(email);
		if (user.isPresent()) {
			throw new DuplicateEmailException();
		}
	}

	@Transactional
	public void checkFromRedundancyNickname(CheckOfRedundancyNicknameReqDto reqDto) {
		String nickname = reqDto.getNickname();
		Optional<User> user = userRepository.findByName(nickname);
		if (user.isPresent()) {
			throw new DuplicateNicknameException();
		}
	}
}
