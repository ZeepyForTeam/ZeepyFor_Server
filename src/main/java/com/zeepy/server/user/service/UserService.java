package com.zeepy.server.user.service;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zeepy.server.auth.repository.TokenRepository;
import com.zeepy.server.common.CustomExceptionHandler.CustomException.DuplicateEmailException;
import com.zeepy.server.common.CustomExceptionHandler.CustomException.DuplicateNicknameException;
import com.zeepy.server.common.CustomExceptionHandler.CustomException.NotFoundUserException;
import com.zeepy.server.user.domain.Address;
import com.zeepy.server.user.domain.ModifyNicknameReqDto;
import com.zeepy.server.user.domain.User;
import com.zeepy.server.user.dto.AddAddressReqDto;
import com.zeepy.server.user.dto.AddressResDto;
import com.zeepy.server.user.dto.CheckOfRedundancyEmailReqDto;
import com.zeepy.server.user.dto.CheckOfRedundancyNicknameReqDto;
import com.zeepy.server.user.dto.ModifyPasswordReqDto;
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

	@Transactional
	public void modifyUser(ModifyNicknameReqDto modifyNicknameReqDto, String userEmail) {
		User user = userRepository.findByEmail(userEmail)
			.orElseThrow(NotFoundUserException::new);
		user.setName(modifyNicknameReqDto
			.getNickname());
	}

	@Transactional
	public void modifyPassword(ModifyPasswordReqDto modifyPasswordReqDto, String userEmail) {
		User user = userRepository.findByEmail(userEmail)
			.orElseThrow(NotFoundUserException::new);
		user.setPassword(modifyPasswordReqDto
			.getPassword());
	}

	@Transactional
	public void memberShipWithdrawal(String userEmail) {
		User user = userRepository.findByEmail(userEmail).orElseThrow(NotFoundUserException::new);

		tokenRepository.deleteByUserId(user
			.getId());

		userRepository.delete(user);
	}

	@Transactional
	public void addAddress(AddAddressReqDto addAddressReqDto) {
		@Deprecated
		User user = userRepository.findById(addAddressReqDto.getUserId())
			.orElseThrow(NotFoundUserException::new);

		user.setAddress(addAddressReqDto.getAddresses().stream()
			.map(Address::new)
			.collect(Collectors.toList()));
	}

	@Transactional
	public AddressResDto getAddresses(Long id) {
		User user = userRepository.findById(id).orElseThrow(NotFoundUserException::new);
		return new AddressResDto(user.getAddresses());
	}
}
