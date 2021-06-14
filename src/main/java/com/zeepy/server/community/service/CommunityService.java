package com.zeepy.server.community.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zeepy.server.common.CustomExceptionHandler.CustomException.NoSuchCommunityException;
import com.zeepy.server.common.CustomExceptionHandler.CustomException.NoSuchUserException;
import com.zeepy.server.community.domain.Community;
import com.zeepy.server.community.domain.CommunityLike;
import com.zeepy.server.community.dto.CommunityLikeDto;
import com.zeepy.server.community.dto.CommunityLikeRequestDto;
import com.zeepy.server.community.dto.CommunityResponseDto;
import com.zeepy.server.community.dto.CommunityResponseDtos;
import com.zeepy.server.community.dto.SaveCommunityRequestDto;
import com.zeepy.server.community.repository.CommunityLikeRepository;
import com.zeepy.server.community.repository.CommunityRepository;
import com.zeepy.server.user.domain.User;
import com.zeepy.server.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommunityService {
	private final CommunityRepository communityRepository;
	private final CommunityLikeRepository communityLikeRepository;
	private final UserRepository userRepository;

	@Transactional
	public Long save(SaveCommunityRequestDto requestDto) {
		Community community = communityRepository.save(requestDto.toEntity());
		return community.getId();
	}

	@Transactional
	public Long like(CommunityLikeRequestDto requestDto) {
		Community community = communityRepository.findById(requestDto.getCommunityId())
			.orElseThrow(NoSuchCommunityException::new);

		User user = userRepository.findById(requestDto.getUserId())
			.orElseThrow(NoSuchUserException::new);

		CommunityLikeDto communityLikeDto = new CommunityLikeDto(user, community);
		CommunityLike communityLike = communityLikeRepository.save(communityLikeDto.toEntity());

		return communityLike.getId();
	}

	@Transactional
	public void cancelLike(CommunityLikeRequestDto requestDto) {
		Community community = communityRepository.findById(requestDto.getCommunityId())
			.orElseThrow(NoSuchCommunityException::new);

		User user = userRepository.findById(requestDto.getUserId())
			.orElseThrow(NoSuchUserException::new);

		CommunityLike communityLike = communityLikeRepository.findByCommunityAndUser(community, user);
		communityLikeRepository.deleteById(communityLike.getId());
	}

	@Transactional(readOnly = true)
	public CommunityResponseDtos getLikeList(Long id) {
		List<CommunityLike> communityLikeList = communityLikeRepository.findAllByUserId(id);
		return new CommunityResponseDtos(communityLikeList.stream()
			.map(CommunityLike::getCommunity)
			.map(CommunityResponseDto::new)
			.collect(Collectors.toList()));
	}
}

