package com.zeepy.server.user.service;

import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zeepy.server.common.CustomExceptionHandler.CustomException.NotFoundUserException;
import com.zeepy.server.user.domain.Address;
import com.zeepy.server.user.domain.User;
import com.zeepy.server.user.dto.AddAddressReqDto;
import com.zeepy.server.user.dto.AddressResDto;
import com.zeepy.server.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
	private final UserRepository userRepository;

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
		return new AddressResDto(user.getAddresss());
	}
}
