package com.zeepy.server.auth.service;

import org.springframework.stereotype.Service;

import com.zeepy.server.auth.dto.AppleTokenResDto;
import com.zeepy.server.auth.model.Payload;
import com.zeepy.server.auth.model.TokenResponse;
import com.zeepy.server.auth.repository.TokenRepository;
import com.zeepy.server.auth.utils.AppleUtils;
import com.zeepy.server.common.config.security.JwtAuthenticationProvider;
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
		return null;    //exception으로 변경해정
	}

	public AppleTokenResDto requestCodeValidations(String clientSecret, String code, String appleRefreshToken) {
		AppleTokenResDto appleTokenResDto = new AppleTokenResDto();
		//서비스의 accessToken과 refresh토큰 추가
		//apple 토큰 Token에 저장.

		if (clientSecret != null && code != null && appleRefreshToken == null) { //최초 로그인
			TokenResponse tokenResponse = appleUtils.validateAuthorizationGrantCode(clientSecret, code);
			if (tokenResponse == null) {
				System.out.println("null 일떄는 어떻게 처리하지");
			}

			//getPayload를 통해 idToken의 정보를 가져온다(email)
			//user를 email을 통해 저장
			//
		}
		if (clientSecret != null && code == null && appleRefreshToken != null) {    //토큰 리프레시 & 기존회원 로그인
			TokenResponse tokenResponse = appleUtils.validateAnExistingRefreshToken(clientSecret, appleRefreshToken);
			if (tokenResponse == null) {
				System.out.println("null 일떄는 어떻게 처리하지");
			}
		}

		return appleTokenResDto;
	}

	public Payload getPayload(String idToken) {
		return appleUtils.decodeFromIdToken(idToken);
	}
}
