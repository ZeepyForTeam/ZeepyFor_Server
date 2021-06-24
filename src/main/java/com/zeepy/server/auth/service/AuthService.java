package com.zeepy.server.auth.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zeepy.server.auth.domain.Token;
import com.zeepy.server.auth.dto.LoginReqDto;
import com.zeepy.server.auth.dto.TokenResDto;
import com.zeepy.server.auth.repository.TokenRepository;
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
	private final TokenRepository tokenRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtAuthenticationProvider jwtAuthenticationProvider;

	@Transactional
	public TokenResDto login(LoginReqDto loginReqDto) {
		User user = getUserByEmail(loginReqDto.getEmail());

		if (!passwordEncoder.matches(
			loginReqDto.getPassword(),
			user.getPassword())) {
			throw new NotFoundPasswordException();
		}

		String accessToken = jwtAuthenticationProvider.createAccessToken(
			user.getEmail());
		String refreshToken = jwtAuthenticationProvider.createRefreshToken();

		//생성된 accessToken, refreshToken 추가
		Token tokens = new Token(accessToken, refreshToken, user);
		tokenRepository.save(tokens);

		return new TokenResDto(accessToken, refreshToken);
	}

	@Transactional
	public void logout(String userEmail) {
		User user = getUserByEmail(userEmail);
		tokenRepository.deleteByUserId(user
			.getId());
	}

	private User getUserByEmail(String authUserEmail) {
		return userRepository.findByEmail(authUserEmail)
			.orElseThrow(NotFoundUserException::new);
	}
}
