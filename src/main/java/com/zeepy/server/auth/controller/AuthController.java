package com.zeepy.server.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zeepy.server.auth.dto.GetUserInfoResDto;
import com.zeepy.server.auth.dto.KakaoLoginReqDto;
import com.zeepy.server.auth.dto.LoginReqDto;
import com.zeepy.server.auth.dto.ReIssueReqDto;
import com.zeepy.server.auth.dto.TokenResDto;
import com.zeepy.server.auth.service.AuthService;
import com.zeepy.server.auth.service.KakaoApi;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/auth")
@RestController
public class AuthController {
	private final AuthService authService;
	private final KakaoApi kakaoApi;

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
		String kakaoAccessToken = kakaoLoginReqDto.getAccessToken();
		GetUserInfoResDto userInfoResDto = kakaoApi.getUserInfo(kakaoAccessToken);

		TokenResDto tokenResDto = authService.kakaoLogin(userInfoResDto, kakaoAccessToken);
		return ResponseEntity.ok().body(tokenResDto);
	}
}
