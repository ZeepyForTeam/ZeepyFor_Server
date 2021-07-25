package com.zeepy.server.community.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zeepy.server.common.CustomExceptionHandler.CustomException.AlreadyParticipationException;
import com.zeepy.server.common.CustomExceptionHandler.CustomException.BadRequestCommentException;
import com.zeepy.server.common.CustomExceptionHandler.CustomException.NotFoundCommunityException;
import com.zeepy.server.common.CustomExceptionHandler.CustomException.NotFoundUserException;
import com.zeepy.server.community.domain.Comment;
import com.zeepy.server.community.domain.Community;
import com.zeepy.server.community.domain.CommunityCategory;
import com.zeepy.server.community.domain.CommunityLike;
import com.zeepy.server.community.domain.Participation;
import com.zeepy.server.community.dto.CancelJoinCommunityRequestDto;
import com.zeepy.server.community.dto.CommentDto;
import com.zeepy.server.community.dto.CommunityLikeDto;
import com.zeepy.server.community.dto.CommunityLikeRequestDto;
import com.zeepy.server.community.dto.CommunityResponseDto;
import com.zeepy.server.community.dto.CommunityResponseDtos;
import com.zeepy.server.community.dto.JoinCommunityRequestDto;
import com.zeepy.server.community.dto.MyZipJoinResDto;
import com.zeepy.server.community.dto.ParticipationDto;
import com.zeepy.server.community.dto.ParticipationResDto;
import com.zeepy.server.community.dto.SaveCommunityRequestDto;
import com.zeepy.server.community.dto.UpdateCommunityReqDto;
import com.zeepy.server.community.dto.WriteCommentRequestDto;
import com.zeepy.server.community.dto.WriteOutResDto;
import com.zeepy.server.community.repository.CommentRepository;
import com.zeepy.server.community.repository.CommunityLikeRepository;
import com.zeepy.server.community.repository.CommunityRepository;
import com.zeepy.server.community.repository.ParticipationRepository;
import com.zeepy.server.user.domain.User;
import com.zeepy.server.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommunityService {
	private final CommunityRepository communityRepository;
	private final CommunityLikeRepository communityLikeRepository;
	private final UserRepository userRepository;
	private final ParticipationRepository participationRepository;
	private final CommentRepository commentRepository;

	@Transactional
	public Long like(CommunityLikeRequestDto requestDto) {
		Community community = communityRepository.findById(requestDto.getCommunityId())
			.orElseThrow(NotFoundCommunityException::new);

		User user = userRepository.findById(requestDto.getUserId())
			.orElseThrow(NotFoundUserException::new);

		CommunityLikeDto communityLikeDto = new CommunityLikeDto(user, community);
		CommunityLike communityLike = communityLikeRepository.save(communityLikeDto.toEntity());

		return communityLike.getId();
	}

	@Transactional
	public void cancelLike(CommunityLikeRequestDto requestDto) {
		Community community = communityRepository.findById(requestDto.getCommunityId())
			.orElseThrow(NotFoundCommunityException::new);

		User user = userRepository.findById(requestDto.getUserId())
			.orElseThrow(NotFoundUserException::new);

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

	@Transactional
	public Long save(SaveCommunityRequestDto requestDto) {
		Long writerId = requestDto.getWriterId();
		User writer = userRepository.findById(writerId)
			.orElseThrow(NotFoundUserException::new);
		requestDto.setUser(writer);

		Community communityToSave = requestDto.toEntity();
		Community community = communityRepository.save(communityToSave);

		ParticipationDto participationDto = new ParticipationDto(community, writer);
		Participation participationToSave = participationDto.toEntity();
		participationRepository.save(participationToSave);

		return community.getId();
	}

	@Transactional
	public void joinCommunity(Long communityId, JoinCommunityRequestDto joinCommunityRequestDto) {
		Community community = communityRepository.findById(communityId)
			.orElseThrow(NotFoundCommunityException::new);

		Long participationUserId = joinCommunityRequestDto.getParticipationUserId();
		User participants = userRepository.findById(participationUserId)
			.orElseThrow(NotFoundUserException::new);

		participationRepository.findByCommunityIdAndUserId(communityId, participationUserId)
			.ifPresent(v -> {
				throw new AlreadyParticipationException();
			});

		ParticipationDto participationDto = new ParticipationDto(community, participants);
		Participation participationToSave = participationDto.toUpdateEntity();
		participationRepository.save(participationToSave);

		String comment = joinCommunityRequestDto.getComment();
		Boolean isSecret = joinCommunityRequestDto.getIsSecret();
		CommentDto commentDto = new CommentDto(comment, isSecret, true, null, community, participants);
		commentRepository.save(commentDto.toEntity());
	}

	@Transactional
	public void cancelJoinCommunity(Long communityId, CancelJoinCommunityRequestDto cancelJoinCommunityRequestDto) {
		Long cancelJoinUserId = cancelJoinCommunityRequestDto.getCancelUserId();
		User findUser = userRepository.findById(cancelJoinUserId)
			.orElseThrow(NotFoundUserException::new);

		Community findCommunity = communityRepository.findById(communityId)
			.orElseThrow(NotFoundCommunityException::new);

		Long findUserId = findUser.getId();
		Long findCommunityId = findCommunity.getId();
		participationRepository.deleteByUserIdAndCommunityId(findUserId, findCommunityId);
		List<Comment> comments = commentRepository.findCommentsByUserIdAndCommunityId(findUserId, findCommunityId);

		Comment superComment = comments.stream().filter(v ->
			v.getSuperComment() == null &&
				v.getIsParticipation())
			.findFirst()
			.orElseThrow(BadRequestCommentException::new);
		superComment.cancelParticipation();

		findCommunity.substractCurrentNumberOfPeople();

	}

	@Transactional
	public void saveComment(Long communityId, WriteCommentRequestDto writeCommentRequestDto) {
		Long writerUserId = writeCommentRequestDto.getWriteUserId();
		Long superCommentId = writeCommentRequestDto.getSuperCommentId();
		User writer = userRepository.findById(writerUserId)
			.orElseThrow(NotFoundUserException::new);
		Community community = communityRepository.findById(communityId)
			.orElseThrow(NotFoundCommunityException::new);

		Comment superComment = null;
		if (superCommentId != null) {
			superComment = commentRepository.findById(superCommentId)
				.orElseThrow(BadRequestCommentException::new);
		}

		String comment = writeCommentRequestDto.getComment();
		Boolean isSecret = writeCommentRequestDto.getIsSecret();
		CommentDto commentDto = CommentDto.builder()
			.comment(comment)
			.isSecret(isSecret)
			.superComment(superComment)
			.community(community)
			.writer(writer)
			.build();
		commentRepository.save(commentDto
			.toEntity());
	}

	@Transactional(readOnly = true)
	public MyZipJoinResDto getJoinList(Long userId) {
		List<Participation> participationList = participationRepository.findAllByUserId(userId);
		List<Community> communityList = communityRepository.findAllByUserId(userId);

		List<ParticipationResDto> participationResDtoList = participationList.stream()
			.map(ParticipationResDto::new)
			.collect(Collectors.toList());
		List<WriteOutResDto> writeOutResDtoList = communityList.stream()
			.map(WriteOutResDto::new)
			.collect(Collectors.toList());

		return new MyZipJoinResDto(participationResDtoList, writeOutResDtoList);
	}

	@Transactional
	public void updateCommunity(Long communityId, UpdateCommunityReqDto updateCommunityReqDto) {
		Community findCommunity = communityRepository.findById(communityId)
			.orElseThrow(NotFoundCommunityException::new);
		updateCommunityReqDto.updateCommunity(findCommunity);
	}

	@Transactional(readOnly = true)
	public CommunityResponseDto getCommunity(Long communityId) {
		Community community = communityRepository.findById(communityId)
			.orElseThrow(NotFoundCommunityException::new);
		return new CommunityResponseDto(community);
	}

	@Transactional(readOnly = true)
	public CommunityResponseDtos getCommunityList(String address, String communityType) {
		if (address == null) {
			if (communityType == null) {
				return CommunityResponseDto.ofList(communityRepository.findAll());
			}
			return CommunityResponseDto.ofList(
				communityRepository.findByCategory(CommunityCategory.valueOf(communityType)));
		}

		if (communityType == null) {
			return CommunityResponseDto.ofList(communityRepository.findByAddress(address));
		}
		return CommunityResponseDto.ofList(
			communityRepository.findByAddressAndCommunityCategory(address, CommunityCategory.valueOf(communityType)));
	}
}

