package com.zeepy.server.s3.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zeepy.server.s3.dto.GetPresignedUrlResDto;
import com.zeepy.server.s3.utils.S3Util;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/s3")
@RequiredArgsConstructor
public class S3Controller {
	private final S3Util s3Util;

	@GetMapping
	public ResponseEntity<GetPresignedUrlResDto> getPresignedUrl() {
		GetPresignedUrlResDto getPresignedUrlResDto = s3Util.GeneratePresignedUrlAndUploadObject();
		System.out.println("url : " + getPresignedUrlResDto.getPresignedUrl());
		return ResponseEntity.ok().body(getPresignedUrlResDto);
	}
}
