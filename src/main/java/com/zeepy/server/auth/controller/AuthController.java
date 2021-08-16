package com.zeepy.server.auth.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zeepy.server.auth.dto.AppleRefreshReqDto;
import com.zeepy.server.auth.dto.AppleServiceResDto;
import com.zeepy.server.auth.dto.AppleTokenResDto;
import com.zeepy.server.auth.dto.GetUserInfoResDto;
import com.zeepy.server.auth.dto.LoginReqDto;
import com.zeepy.server.auth.dto.ReIssueReqDto;
import com.zeepy.server.auth.dto.SNSLoginReqDto;
import com.zeepy.server.auth.dto.TokenResDto;
import com.zeepy.server.auth.model.TokenResponse;
import com.zeepy.server.auth.service.AppleService;
import com.zeepy.server.auth.service.AuthService;
import com.zeepy.server.auth.service.KakaoApi;
import com.zeepy.server.auth.service.NaverApi;
import com.zeepy.server.common.CustomExceptionHandler.CustomException.BadRequestBodyException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/auth")
@RestController
public class AuthController {
	private final AppleService appleService;

	private final AuthService authService;
	private final KakaoApi kakaoApi;
	private final NaverApi naverApi;
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
	public ResponseEntity<TokenResDto> kakaoLogin(@RequestBody SNSLoginReqDto snsLoginReqDto) {
		GetUserInfoResDto userInfoResDto = kakaoApi.getUserInfo(
			snsLoginReqDto.getAccessToken());

		TokenResDto tokenResDto = authService.kakaoLogin(userInfoResDto, snsLoginReqDto);
		return ResponseEntity.ok().body(tokenResDto);
	}

	@PostMapping("/login/naver")
	public ResponseEntity<TokenResDto> naverLogin(@RequestBody SNSLoginReqDto snsLoginReqDto) {
		GetUserInfoResDto userInfoResDto = naverApi.getUserInfo(
			snsLoginReqDto.getAccessToken());

		TokenResDto tokenResDto = authService.naverLogin(userInfoResDto, snsLoginReqDto);
		return ResponseEntity.ok().body(tokenResDto);
	}

	@PostMapping("/login/apple")
	public ResponseEntity<AppleTokenResDto> appleLogin(@RequestBody AppleServiceResDto appleServiceResDto) {

		if (appleServiceResDto == null) {
			System.out.println("안왔는뎅??????????????");
			throw new BadRequestBodyException();
		}

		String code = appleServiceResDto.getCode();
		String idToken = appleServiceResDto.getId_token();
		String clientSecret = appleService.getAppleClientSecret(idToken);

		System.out.println("================================");
		System.out.println("id_token ‣ " + appleServiceResDto.getId_token());
		System.out.println("payload ‣ " + appleService.getPayload(appleServiceResDto.getId_token()));
		System.out.println("code ‣ " + code);
		System.out.println("client_secret ‣ " + clientSecret);
		System.out.println("================================");

		TokenResponse tokenResponse = appleService.requestCodeValidations(clientSecret, code, null);
		AppleTokenResDto appleTokenResDto = appleService.setAppleTokenResDto(tokenResponse, idToken, clientSecret);
		return ResponseEntity.ok().body(appleTokenResDto);
	}

	@PostMapping("/reissue/apple")
	public ResponseEntity<AppleTokenResDto> appleRefresh(@RequestBody AppleRefreshReqDto appleRefreshReqDto) {
		String appleRefreshToken = appleRefreshReqDto.getAppleRefreshToken();
		String clientSecret = appleService.getClientSecretByModel(appleRefreshToken);
		System.out.println("clientSecret : " + clientSecret);

		TokenResponse tokenResponse = appleService.requestCodeValidations(clientSecret, null, appleRefreshToken);
		AppleTokenResDto appleTokenResDto = appleService.setAppleTokenResDto(tokenResponse, null, clientSecret);
		return ResponseEntity.ok().body(appleTokenResDto);
	}

	@GetMapping("/login/test")
	public ResponseEntity<Void> test(@RequestParam("code") String code, @RequestParam("state") String state) {
		System.out.println("code : " + code);
		System.out.println("state : " + state);
		return ResponseEntity.ok().build();
	}
}
