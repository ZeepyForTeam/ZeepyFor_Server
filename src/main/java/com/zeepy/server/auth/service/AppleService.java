package com.zeepy.server.auth.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zeepy.server.auth.domain.Token;
import com.zeepy.server.auth.dto.AppleTokenResDto;
import com.zeepy.server.auth.dto.GetUserInfoResDto;
import com.zeepy.server.auth.model.Payload;
import com.zeepy.server.auth.model.TokenResponse;
import com.zeepy.server.auth.repository.TokenRepository;
import com.zeepy.server.auth.utils.AppleUtils;
import com.zeepy.server.common.CustomExceptionHandler.CustomException.AppleUnAuthrizationException;
import com.zeepy.server.common.CustomExceptionHandler.CustomException.SNSUnAuthorization;
import com.zeepy.server.common.config.security.JwtAuthenticationProvider;
import com.zeepy.server.user.domain.User;
import com.zeepy.server.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AppleService {
	private final AppleUtils appleUtils;
	private final TokenRepository tokenRepository;
	private final UserRepository userRepository;
	private final JwtAuthenticationProvider jwtAuthenticationProvider;

	public String getAppleClientSecret(String idToken) {
		if (appleUtils.verifyIdentityToken(idToken)) {
			return appleUtils.createClientSecret();
		}
		throw new AppleUnAuthrizationException();
	}

	public TokenResponse requestCodeValidations(String clientSecret, String code) {
		System.out.println("최초 애플 로그인");
		TokenResponse tokenResponse = appleUtils.validateAuthorizationGrantCode(clientSecret, code);
		if (tokenResponse == null) {
			throw new SNSUnAuthorization();
		}
		System.out.println("통신성공!!!!");
		return tokenResponse;
	}

	@Transactional
	public AppleTokenResDto setAppleTokenResDto(TokenResponse tokenResponse,String code) {
//		String idToken = tokenResponse.getId_token();

		String appleRefreshToken = tokenResponse.getRefresh_token();
//		Payload payload = getPayload(idToken);
//		String userEmail = payload.getEmail();
		String userEmail = code;

		User findUser = userRepository.findByEmail(userEmail)
			.orElseGet(() -> {
				System.out.println("기존 등록된 사용자가없습니다.");
				GetUserInfoResDto UserInfoResDto = new GetUserInfoResDto(userEmail);
				User newUser = UserInfoResDto.toEntity();
				User saveUser = userRepository.save(newUser);
				saveUser.setNickNameById();
				System.out.println("저장된 사용자의 이름 : " + saveUser.getName());
				return saveUser;
			});

		System.out.println("가져온 사용자의 이름 : " + findUser.getName());
		String accessToken = jwtAuthenticationProvider.createAccessToken(findUser.getEmail());
		String refreshToken = jwtAuthenticationProvider.createRefreshToken();

		Token findToken = tokenRepository.findByUserId(findUser
			.getId())
			.orElseGet(() -> new Token(findUser));

		findToken.setServiceToken(accessToken, refreshToken);
		findToken.setAppleRefreshToken(appleRefreshToken);
		tokenRepository.save(findToken);
		return new AppleTokenResDto(accessToken, refreshToken, appleRefreshToken, findUser.getId());
	}

	public Payload getPayload(String idToken) {
		return appleUtils.decodeFromIdToken(idToken);
	}
}
