package com.zeepy.server.auth.service;

import org.springframework.stereotype.Service;

import com.zeepy.server.auth.domain.Token;
import com.zeepy.server.auth.dto.AppleTokenResDto;
import com.zeepy.server.auth.dto.GetUserInfoResDto;
import com.zeepy.server.auth.model.Payload;
import com.zeepy.server.auth.model.TokenResponse;
import com.zeepy.server.auth.repository.TokenRepository;
import com.zeepy.server.auth.utils.AppleUtils;
import com.zeepy.server.common.CustomExceptionHandler.CustomException.AppleUnAuthrizationException;
import com.zeepy.server.common.CustomExceptionHandler.CustomException.NotFoundTokenException;
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

	public TokenResponse requestCodeValidations(String clientSecret, String code, String appleRefreshToken) {
		TokenResponse tokenResponse = new TokenResponse();

		if (clientSecret != null && code != null && appleRefreshToken == null) { //최초 로그인
			System.out.println("최초 애플 로그인");
			tokenResponse = appleUtils.validateAuthorizationGrantCode(clientSecret, code);
			if (tokenResponse == null) {
				System.out.println("null 일떄는 어떻게 처리하지");
			}
		}
		if (clientSecret != null && code == null && appleRefreshToken != null) {    //토큰 리프레시 & 기존회원 로그인
			System.out.println("기존회원 애플 로그인");
			tokenResponse = appleUtils.validateAnExistingRefreshToken(clientSecret, appleRefreshToken);
			if (tokenResponse == null) {
				System.out.println("null 일떄는 어떻게 처리하지");
			}
		}
		System.out.println("통신성공!!!!");
		return tokenResponse;
	}

	public AppleTokenResDto setAppleTokenResDto(TokenResponse tokenResponse, String idToken) {

		if (idToken == null) {
			Token token = getTokenByAppleRefreshToken(tokenResponse.getRefresh_token());
			idToken = token.getAppleIdToken();
		}

		String appleRefreshToken = tokenResponse.getRefresh_token();
		Payload payload = getPayload(idToken);
		String userEmail = payload.getEmail();

		User findUser = userRepository.findByEmail(userEmail)
			.orElseGet(() -> {
				GetUserInfoResDto UserInfoResDto = new GetUserInfoResDto(userEmail);
				User newUser = UserInfoResDto.toEntity();
				User saveUser = userRepository.save(newUser);
				saveUser.setNameById();
				return saveUser;
			});

		String accessToken = jwtAuthenticationProvider.createAccessToken(findUser.getEmail());
		String refreshToken = jwtAuthenticationProvider.createRefreshToken();

		Token token = new Token(accessToken, refreshToken, findUser);
		token.setAppleRefreshToken(appleRefreshToken);
		tokenRepository.save(token);
		return new AppleTokenResDto(accessToken, refreshToken, appleRefreshToken, findUser.getId());
	}

	public String getClientSecretByModel(String appleRefreshToken) {
		Token token = getTokenByAppleRefreshToken(appleRefreshToken);
		return token.getAppleClientSecret();
	}

	public Payload getPayload(String idToken) {
		return appleUtils.decodeFromIdToken(idToken);
	}

	private Token getTokenByAppleRefreshToken(String appleRefreshToken) {
		return tokenRepository.findByAppleRefreshToken(appleRefreshToken)
			.orElseThrow(NotFoundTokenException::new);
	}
}
