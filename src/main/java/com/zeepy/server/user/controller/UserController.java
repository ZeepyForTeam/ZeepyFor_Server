package com.zeepy.server.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zeepy.server.user.dto.AddAddressReqDto;
import com.zeepy.server.user.dto.AddressResDto;
import com.zeepy.server.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/user")
@RestController
public class UserController {
	private final UserService userService;

	@PostMapping("/address")
	public ResponseEntity<Void> addAddress(@RequestBody AddAddressReqDto addAddressReqDto) {
		userService.addAddress(addAddressReqDto);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/address/{id}")
	public ResponseEntity<AddressResDto> getAddresses(@PathVariable("id") Long userId) {
		AddressResDto addressResDto = userService.getAddresses(userId);
		return ResponseEntity.ok().body(addressResDto);

	}
}
