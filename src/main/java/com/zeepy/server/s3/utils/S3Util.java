package com.zeepy.server.s3.utils;

import java.net.URL;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.amazonaws.HttpMethod;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.zeepy.server.s3.dto.GetPresignedUrlResDto;

@Component
@PropertySource(value = "classpath:config/application-local.properties")
public class S3Util {

	@Value("${cloud.aws.region.static}")
	private String clientRegion;
	@Value("${cloud.aws.s3.bucket}")
	private String bucketName;
	@Value("${cloud.aws.credentials.accessKey}")
	private String accessKey;
	@Value("${cloud.aws.credentials.secretKey}")
	private String secretKey;

	public GetPresignedUrlResDto GeneratePresignedUrlAndUploadObject() {
		String preSignedUrl;
		String fileName = "zeepyImage/" + UUID.randomUUID() + ".jpeg";
		Date expiration = new Date();
		long expTimeMillis = expiration.getTime();
		expTimeMillis += 1000 * 60 * 5; // 5분
		expiration.setTime(expTimeMillis);

		try {
			BasicAWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
			AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
				.withRegion(clientRegion)
				.withCredentials(new AWSStaticCredentialsProvider(credentials) {
				})
				.build();
			System.out.println("s3Client : " + s3Client);

			GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName,
				fileName)
				.withMethod(HttpMethod.PUT)
				.withExpiration(expiration);
			System.out.println("generatePresignedUrlRequest : " + generatePresignedUrlRequest);

			generatePresignedUrlRequest.addRequestParameter(Headers.S3_CANNED_ACL,
				CannedAccessControlList.PublicRead.toString());

			URL url = s3Client.generatePresignedUrl(generatePresignedUrlRequest);
			System.out.println("url : " + url);
			preSignedUrl = url.toExternalForm();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return new GetPresignedUrlResDto(preSignedUrl);

	}
}
