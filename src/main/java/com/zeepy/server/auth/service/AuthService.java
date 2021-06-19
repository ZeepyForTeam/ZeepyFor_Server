package com.zeepy.server.auth.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.zeepy.server.auth.dto.LoginReqDto;
import com.zeepy.server.auth.dto.TokenResDto;
import com.zeepy.server.common.CustomExceptionHandler.CustomException.NotFoundPasswordException;
import com.zeepy.server.common.CustomExceptionHandler.CustomException.NotFoundUserException;
import com.zeepy.server.common.config.security.JwtAuthenticationProvider;
import com.zeepy.server.user.domain.User;
import com.zeepy.server.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AuthService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtAuthenticationProvider jwtAuthenticationProvider;

	public TokenResDto login(LoginReqDto loginReqDto) {
		User user = userRepository.findByEmail(loginReqDto
			.getEmail())
			.orElseThrow(NotFoundUserException::new);
		if (!passwordEncoder.matches(
			loginReqDto.getPassword(),
			user.getPassword())) {
			throw new NotFoundPasswordException();
		}

		String accessToken = jwtAuthenticationProvider.createAccessToken(
			user.getEmail());
		String refreshToken = jwtAuthenticationProvider.createRefreshToken();

		//생성된 accessToken, refreshToken 추가

		return new TokenResDto(accessToken, refreshToken);
	}

	public void logout(UserDetails userDetails) {
		//tokenDB에 저장되어있는 accessToken, refreshToken삭제
	}
}
