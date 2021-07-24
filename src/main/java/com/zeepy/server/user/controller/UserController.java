package com.zeepy.server.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
