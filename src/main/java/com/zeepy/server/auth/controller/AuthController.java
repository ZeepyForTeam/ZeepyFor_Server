package com.zeepy.server.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zeepy.server.auth.dto.LoginReqDto;
import com.zeepy.server.auth.dto.TokenResDto;
import com.zeepy.server.auth.service.AuthService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/auth")
@RestController
public class AuthController {
	private final AuthService authService;

	@PostMapping("/login")
	public ResponseEntity<TokenResDto> login(@RequestBody LoginReqDto loginReqDto) {
		TokenResDto tokenResDto = authService.login(loginReqDto);
		return ResponseEntity.ok().body(tokenResDto);
	}

	@GetMapping("/logout")
	public ResponseEntity<Void> logout(@AuthenticationPrincipal String userEmail) {
		authService.logout(userEmail);
		return ResponseEntity.ok().build();
	}
}
