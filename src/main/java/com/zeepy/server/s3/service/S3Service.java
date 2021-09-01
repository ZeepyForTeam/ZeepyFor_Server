package com.zeepy.server.s3.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.zeepy.server.common.CustomExceptionHandler.CustomException.NotFoundUserException;
import com.zeepy.server.s3.dto.GetPresignedUrlResDto;
import com.zeepy.server.s3.dto.ImageUrlResDto;
import com.zeepy.server.s3.dto.ImageUrlResDtos;
import com.zeepy.server.s3.utils.S3Uploader;
import com.zeepy.server.s3.utils.S3Util;
import com.zeepy.server.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class S3Service {
	private final S3Util s3Util;
	private final S3Uploader s3Uploader;
	private final UserRepository userRepository;

	public GetPresignedUrlResDto getPresignedUrl() {
		GetPresignedUrlResDto getPresignedUrlResDto = s3Util.GeneratePresignedUrlAndUploadObject();
		System.out.println("url : " + getPresignedUrlResDto.getPresignedUrl());
		return getPresignedUrlResDto;
	}

	public ImageUrlResDtos uploadImages(String userEmail, List<MultipartFile> images) {
		userRepository
			.findByEmail(userEmail)
			.orElseThrow(NotFoundUserException::new);

		return images.stream()
			.map(image -> {
				try {
					return new ImageUrlResDto(s3Uploader.upload(image));
				} catch (IOException e) {
					e.printStackTrace();
				}
				return new ImageUrlResDto();
			})
			.collect(Collectors.collectingAndThen(Collectors.toList(), ImageUrlResDtos::new));
	}
}
