package com.zeepy.server.user.service;

import com.zeepy.server.common.CustomExceptionHandler.CustomException.NotFoundUserException;
import com.zeepy.server.user.domain.User;
import com.zeepy.server.user.dto.AccessNotifyRequestDto;
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

	@Transactional
	public void setAccessNotify(AccessNotifyRequestDto accessNotifyRequestDto, String email) {
		User user = userRepository.findByEmail(email).orElseThrow(NotFoundUserException::new);
		user.setAccessNotify(accessNotifyRequestDto.getAccessNotify());
		userRepository.save(user);
	}
}
