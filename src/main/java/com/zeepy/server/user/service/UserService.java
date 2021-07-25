package com.zeepy.server.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zeepy.server.auth.repository.TokenRepository;
import com.zeepy.server.common.CustomExceptionHandler.CustomException.NotFoundUserException;
import com.zeepy.server.user.domain.User;
import com.zeepy.server.user.dto.RegistrationReqDto;
import com.zeepy.server.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
	private final UserRepository userRepository;
	private final TokenRepository tokenRepository;

	@Transactional
	public void registration(RegistrationReqDto registrationReqDto) {
		userRepository.save(registrationReqDto
			.toEntity());
	}

	@Transactional
	public void memberShipWithdrawal(String userEmail) {
		User user = userRepository.findByEmail(userEmail).orElseThrow(NotFoundUserException::new);

		tokenRepository.deleteByUserId(user
			.getId());

		userRepository.delete(user);
	}
}
