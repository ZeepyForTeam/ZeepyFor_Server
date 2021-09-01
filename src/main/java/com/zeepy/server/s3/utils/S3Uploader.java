package com.zeepy.server.s3.utils;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class S3Uploader {
	private final AmazonS3Client amazonS3Client;

	@Value("${cloud.aws.s3.bucket}")
	public String bucket;  // S3 버킷 이름

	public String upload(MultipartFile multipartFile) throws IOException {
		String fileName = multipartFile.getOriginalFilename();

		amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, multipartFile.getInputStream(), null)
			.withCannedAcl(CannedAccessControlList.PublicRead));

		return amazonS3Client.getUrl(bucket, fileName).toString();
	}
}