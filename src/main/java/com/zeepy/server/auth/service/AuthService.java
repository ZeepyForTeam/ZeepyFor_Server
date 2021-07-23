package com.zeepy.server.auth.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zeepy.server.auth.domain.Token;
import com.zeepy.server.auth.dto.GetUserInfoResDto;
import com.zeepy.server.auth.dto.LoginReqDto;
import com.zeepy.server.auth.dto.ReIssueReqDto;
import com.zeepy.server.auth.dto.TokenResDto;
import com.zeepy.server.auth.repository.TokenRepository;
import com.zeepy.server.common.CustomExceptionHandler.CustomException.NotFoundPasswordException;
import com.zeepy.server.common.CustomExceptionHandler.CustomException.NotFoundUserException;
import com.zeepy.server.common.CustomExceptionHandler.CustomException.RefreshTokenException;
import com.zeepy.server.common.CustomExceptionHandler.CustomException.RefreshTokenNotExistException;
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

	@Transactional
	public TokenResDto reissue(ReIssueReqDto reIssueReqDto) {
		String accessToken = reIssueReqDto.getAccessToken();
		String refreshToken = reIssueReqDto.getRefreshToken();

		tokenRepository.findByRefreshToken(refreshToken)
			.orElseThrow(RefreshTokenNotExistException::new);

		if (!jwtAuthenticationProvider.validateToken(refreshToken)) {
			throw new RefreshTokenException();
		}

		String userEmail = jwtAuthenticationProvider.getUserEmail(accessToken);
		String newAccessToken = jwtAuthenticationProvider.createAccessToken(userEmail);
		String newRefreshToken = jwtAuthenticationProvider.createRefreshToken();
		return new TokenResDto(newAccessToken, newRefreshToken);
	}

	@Transactional
	public TokenResDto kakaoLogin(GetUserInfoResDto userInfoResDto) {
		String nickname = userInfoResDto.getNickname();
		String email = userInfoResDto.getEmail();

		//신규회원이면 회원가입, 기존회원이면 kakao에서 받은 정보로 최신화후 저장
		User user = userRepository.findByEmail(nickname)
			.map(entity -> entity.update(email, nickname))
			.orElseGet(userInfoResDto::toEntity);
		userRepository.save(user);

		String accessToken = jwtAuthenticationProvider.createAccessToken(user.getEmail());
		String refreshToken = jwtAuthenticationProvider.createRefreshToken();

		Token tokens = new Token(accessToken, refreshToken, user);
		tokenRepository.save(tokens);

		return new TokenResDto(accessToken, refreshToken);
	}

	private User getUserByEmail(String authUserEmail) {
		return userRepository.findByEmail(authUserEmail)
			.orElseThrow(NotFoundUserException::new);
	}
}
