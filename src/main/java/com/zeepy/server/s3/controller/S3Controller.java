package com.zeepy.server.s3.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.zeepy.server.s3.dto.GetPresignedUrlResDto;
import com.zeepy.server.s3.dto.ImageUrlResDtos;
import com.zeepy.server.s3.service.S3Service;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/s3")
@RequiredArgsConstructor
public class S3Controller {
	private final S3Service s3Service;

	@GetMapping
	public ResponseEntity<GetPresignedUrlResDto> getPresignedUrl() {
		return ResponseEntity.ok().body(s3Service.getPresignedUrl());
	}

	@PostMapping
	public ResponseEntity<ImageUrlResDtos> uploadImage(
		@RequestPart(value = "file", required = false) List<MultipartFile> images,
		@AuthenticationPrincipal String userEmail
	) {
		return ResponseEntity.ok().body(s3Service.uploadImages(userEmail, images));
	}
}
