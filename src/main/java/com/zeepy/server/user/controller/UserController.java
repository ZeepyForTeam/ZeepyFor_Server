package com.zeepy.server.user.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zeepy.server.user.domain.ModifyNicknameReqDto;
import com.zeepy.server.user.dto.AddAddressReqDto;
import com.zeepy.server.user.dto.AddressResDto;
import com.zeepy.server.user.dto.CheckOfRedundancyEmailReqDto;
import com.zeepy.server.user.dto.CheckOfRedundancyNicknameReqDto;
import com.zeepy.server.user.dto.ModifyPasswordReqDto;
import com.zeepy.server.user.dto.RegistrationReqDto;
import com.zeepy.server.user.dto.SendMailCheckResDto;
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

	@PostMapping("/address")
	public ResponseEntity<Void> addAddress(
		@RequestBody AddAddressReqDto addAddressReqDto,
		@AuthenticationPrincipal String userEmail) {
		userService.addAddress(addAddressReqDto, userEmail);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/address")
	public ResponseEntity<AddressResDto> getAddresses(@AuthenticationPrincipal String userEmail) {
		AddressResDto addressResDto = userService.getAddresses(userEmail);
		return ResponseEntity.ok().body(addressResDto);
	}

	@PutMapping("/mail")
	public ResponseEntity<Void> setSendMailCheck(
		@AuthenticationPrincipal String email) {
		userService.setSendMailCheck(email);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/mail")
	public ResponseEntity<SendMailCheckResDto> getSendMailCheck(
		@AuthenticationPrincipal String userEmail
	) {
		SendMailCheckResDto sendMailCheckResDto = userService.getSendMailCheck(userEmail);
		return ResponseEntity.ok().body(sendMailCheckResDto);
	}
}
