package com.zeepy.server.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zeepy.server.auth.dto.AppleTokenResDto;
import com.zeepy.server.auth.model.Payload;
import com.zeepy.server.auth.model.TokenResponse;
import com.zeepy.server.auth.utils.AppleUtils;

@Service
public class AppleService {
	@Autowired
	AppleUtils appleUtils;

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

		if (clientSecret != null && code != null && appleRefreshToken == null) {
			TokenResponse tokenResponse = appleUtils.validateAuthorizationGrantCode(clientSecret, code);
		}
		if (clientSecret != null && code == null && appleRefreshToken != null) {
			//TokenResponse tokenResponse = appleUtils.validateAnExistingRefreshToken(clientSecret, appleRefreshToken);
		}

		return appleTokenResDto;
	}

	public Payload getPayload(String idToken) {
		return appleUtils.decodeFromIdToken(idToken);
	}
}
