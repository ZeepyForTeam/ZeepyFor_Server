package com.zeepy.server.email.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zeepy.server.email.dto.EmailRequestDto;
import com.zeepy.server.email.service.EmailService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

/**
 * Created by Minky on 2021-07-25
 */

@RestController
@RequestMapping("/api/emails")
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class EmailController {
	private final EmailService emailService;

	@PostMapping
	public ResponseEntity<Void> saveEmail(
		@Valid @RequestBody EmailRequestDto emailRequestDto
	) {
		Long id = emailService.create(emailRequestDto);
		return ResponseEntity.created(URI.create("api/emails/" + id)).build();
	}
}
