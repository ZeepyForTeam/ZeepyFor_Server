package com.zeepy.server.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zeepy.server.user.domain.ModifyNicknameReqDto;
import com.zeepy.server.user.dto.ModifyPasswordReqDto;
import com.zeepy.server.user.dto.RegistrationReqDto;
import com.zeepy.server.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/user")
@RestController
public class UserController {
	private final UserService userService;

	@PostMapping("/registration")
	public ResponseEntity<Void> registration(@RequestBody RegistrationReqDto registrationReqDto) {
		userService.registration(registrationReqDto);
		return ResponseEntity.ok().build();
	}

	@PutMapping("/nickname")
	public ResponseEntity<Void> modifyNickname(@RequestBody ModifyNicknameReqDto modifyNicknameReqDto,
		@AuthenticationPrincipal String userEmail) {
		userService.modifyUser(modifyNicknameReqDto, userEmail);
		return ResponseEntity.ok().build();
	}

	@PutMapping("/password")
	public ResponseEntity<Void> modifyPassword(@RequestBody ModifyPasswordReqDto modifyPasswordReqDto,
		@AuthenticationPrincipal String userEmail) {
		userService.modifyPassword(modifyPasswordReqDto, userEmail);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/withdrawal")
	public ResponseEntity<Void> memberShipWithdrawal(@AuthenticationPrincipal String userEmail) {
		userService.memberShipWithdrawal(userEmail);
		return ResponseEntity.ok().build();
	}
}
