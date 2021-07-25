package com.zeepy.server.user.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zeepy.server.user.dto.CheckOfRedundancyEmailReqDto;
import com.zeepy.server.user.dto.CheckOfRedundancyNicknameReqDto;
import com.zeepy.server.user.dto.RegistrationReqDto;
import com.zeepy.server.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/user")
@RestController
public class UserController {
	private final UserService userService;

	@PostMapping("/registration")
	public ResponseEntity<Void> registration(@Valid @RequestBody RegistrationReqDto registrationReqDto) {
		userService.registration(registrationReqDto);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/redundancy/email")
	public ResponseEntity<Void> checkForRedundancyEmail(
		@RequestBody CheckOfRedundancyEmailReqDto checkOfRedundancyEmailReqDto) {
		userService.checkForRedundancyEmail(checkOfRedundancyEmailReqDto);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/redundancy/nickname")
	public ResponseEntity<Void> checkFromRedundancyNickname(
		@RequestBody CheckOfRedundancyNicknameReqDto checkOfRedundancyNicknameReqDto) {
		userService.checkFromRedundancyNickname(checkOfRedundancyNicknameReqDto);
		return ResponseEntity.ok().build();
	}
}
