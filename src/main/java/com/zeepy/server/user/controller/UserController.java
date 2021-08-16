package com.zeepy.server.user.controller;

import com.zeepy.server.user.dto.AccessNotifyRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.zeepy.server.user.dto.RegistrationReqDto;
import com.zeepy.server.user.service.UserService;

import lombok.RequiredArgsConstructor;

import javax.validation.Valid;

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

	@PatchMapping("notify")
	public ResponseEntity<Void> setAccessNotify(
			@Valid @RequestBody AccessNotifyRequestDto accessNotifyRequestDto,
			@AuthenticationPrincipal String userEmail
	) {
		userService.setAccessNotify(accessNotifyRequestDto, userEmail);
		return ResponseEntity.ok().build();
	}
}
