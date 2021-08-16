package com.zeepy.server.push.service;

import org.springframework.stereotype.Service;

import com.zeepy.server.common.CustomExceptionHandler.CustomException.NotFoundUserException;
import com.zeepy.server.push.dto.PushManyTargetRequestDto;
import com.zeepy.server.push.dto.PushOneTargetRequestDto;
import com.zeepy.server.push.util.FirebaseCloudMessageUtility;
import com.zeepy.server.user.domain.User;
import com.zeepy.server.user.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

/**
 * Created by Minky on 2021-07-21
 */

@Service
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class PushService {
	private final FirebaseCloudMessageUtility firebaseCloudMessageUtility;
	private final UserRepository userRepository;

	public void pushByAllUsers(
		PushManyTargetRequestDto pushManyTargetRequestDto
	) {
		firebaseCloudMessageUtility.sendTopicMessage(
			"notify",
			pushManyTargetRequestDto.getTitle(),
			pushManyTargetRequestDto.getBody()
		);
	}

	public void pushByTargetUsersUsingTopic(
		PushOneTargetRequestDto pushOneTargetRequestDto
	) {
		User user = userRepository.findByEmail(pushOneTargetRequestDto.getEmail())
			.orElseThrow(NotFoundUserException::new);
		firebaseCloudMessageUtility.sendTopicMessage(
			user.getId().toString(),
			pushOneTargetRequestDto.getTitle(),
			pushOneTargetRequestDto.getBody()
		);
	}
}
