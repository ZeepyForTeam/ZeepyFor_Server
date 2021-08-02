package com.zeepy.server.auth.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zeepy.server.auth.dto.AppleRefreshReqDto;
import com.zeepy.server.auth.dto.AppleServiceResDto;
import com.zeepy.server.auth.dto.AppleTokenResDto;
import com.zeepy.server.auth.dto.GetUserInfoResDto;
import com.zeepy.server.auth.dto.KakaoLoginReqDto;
import com.zeepy.server.auth.dto.LoginReqDto;
import com.zeepy.server.auth.dto.ReIssueReqDto;
import com.zeepy.server.auth.dto.TokenResDto;
import com.zeepy.server.auth.service.AppleService;
import com.zeepy.server.auth.service.AuthService;
import com.zeepy.server.auth.service.KakaoApi;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/auth")
@RestController
public class AuthController {
	private final AppleService appleService;

	private final AuthService authService;
	private final KakaoApi kakaoApi;
	private Logger logger = LoggerFactory.getLogger(AuthController.class);

	@PostMapping("/login")
	public ResponseEntity<TokenResDto> login(@RequestBody LoginReqDto loginReqDto) {
		TokenResDto tokenResDto = authService.login(loginReqDto);
		return ResponseEntity.ok().body(tokenResDto);
	}

	@DeleteMapping("/logout")
	public ResponseEntity<Void> logout(@AuthenticationPrincipal String userEmail) {
		authService.logout(userEmail);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/reissue")
	public ResponseEntity<TokenResDto> reissue(@RequestBody ReIssueReqDto reIssueReqDto) {
		TokenResDto tokenResDto = authService.reissue(reIssueReqDto);
		return ResponseEntity.ok().body(tokenResDto);
	}

	@PostMapping("/login/kakao")
	public ResponseEntity<TokenResDto> kakaoLogin(@RequestBody KakaoLoginReqDto kakaoLoginReqDto) {
		GetUserInfoResDto userInfoResDto = kakaoApi.getUserInfo(
			kakaoLoginReqDto.getAccessToken());

		TokenResDto tokenResDto = authService.kakaoLogin(userInfoResDto, kakaoLoginReqDto);
		return ResponseEntity.ok().body(tokenResDto);
	}

	@PostMapping("/login/apple")
	public ResponseEntity<AppleTokenResDto> appleLogin(@RequestBody AppleServiceResDto appleServiceResDto) {

		if (appleServiceResDto == null) {
			System.out.println("안왔는뎅??????????????");
			return null;
		}

		String code = appleServiceResDto.getCode();
		String idToken = appleServiceResDto.getId_token();
		String clientSecret = appleService.getAppleClientSecret(idToken);

		logger.debug("================================");
		logger.debug("id_token ‣ " + appleServiceResDto.getId_token());
		logger.debug("payload ‣ " + appleService.getPayload(appleServiceResDto.getId_token()));
		logger.debug("client_secret ‣ " + clientSecret);
		logger.debug("================================");

		AppleTokenResDto appleTokenResDto = appleService.requestCodeValidations(clientSecret, code, null);
		return ResponseEntity.ok().body(appleTokenResDto);
	}

	@PostMapping("/reissue/apple")
	public ResponseEntity<AppleTokenResDto> appleRefresh(@RequestBody AppleRefreshReqDto appleRefreshReqDto) {
		String appleRefreshToken = appleRefreshReqDto.getAppleRefreshToken();
		//String clientSecret = appleService.getCilentSecret(appleRefreshToken);
		String clientSecret = "";

		AppleTokenResDto appleTokenResDto = appleService.requestCodeValidations(clientSecret, null, appleRefreshToken);
		return ResponseEntity.ok().body(appleTokenResDto);
	}
}
